package common.jpa;

import java.beans.Statement;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Query;

import common.exceptions.SystemException;
import common.utils.StringUtil;
import play.classloading.enhancers.PropertiesEnhancer.PlayPropertyAccessor;
import play.db.Model;
import play.db.jpa.JPA;
import play.db.jpa.JPAModelLoader;

/**
 * This Class lets CRUD’s module choose which columns you want fetch on the
 * query
 *
 * @author jgomes
 */
public class CustomJPAModelLoader extends JPAModelLoader {

    private static final int NOT_ACCESSIBLE_METHOD = Modifier.NATIVE | Modifier.STATIC;
    private static final String SET_PREFIX = "set";
    private static final String GET_PREFIX = "get";
    private final Class<? extends Model> clazz;
    private final String dbName;
    private static final String NULL_STRING = "null";
    private static final String DOT = ".";
    private static final String COMMA = ", ";
    private static final String OR = " OR ";
    private static final String WHERE2 = " WHERE (";
    private static final String SELECT_COUNT_QUERY = "SELECT count(*) ";
    private static final String SPACE_STRING = " ";
    private static final String ID = "id";
    private static final String AND = " AND ";
    private static final String SELECT = "SELECT ";
    private static final String EMPTY_STRING = "";
    private static final String FROM = "FROM ";
    private static final String DESC = "DESC";
    private static final String ASC = "ASC";
    private static final String ORDER_BY = " ORDER BY ";
    private static final String PERCENT_SYMBOL = "%";
    private static final String PARAM_TWO = "?2";
    private static final String WHERE = " WHERE ";
    private static final String PARAM_ONE = "?1";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public CustomJPAModelLoader(final Class<? extends Model> clazz) {
        super(clazz);
        this.clazz = clazz;
        this.dbName = JPA.getDBName(clazz);
    }
    public List<Model> findAll(final String orderBy, final String order) {
        String orderColumn = orderBy;
        String sort = order;
        String q = FROM + this.clazz.getName();
        if (orderColumn == null && sort == null) {
            orderColumn = ID;
            sort = ASC;
        }
        if (orderColumn == null && sort != null) {
            orderColumn = ID;
        }
        if (sort == null || !ASC.equals(sort) && !DESC.equals(sort)) {
            sort = ASC;
        }
        q += ORDER_BY + orderColumn + SPACE_STRING + sort;
        final Query query = JPA.em(this.dbName).createQuery(q);
        return query.getResultList();
    }
    public List<Model> fetch(final CustomJPAModelFilter filter) throws SystemException {
        final StringBuilder builder = this.buildQuery(filter.getColumnsArray(), filter.getOrderBy(), filter.getOrder(), filter.getLstSearchFields(), filter.getKeywords(), filter.getWhere());
        final Query query = JPA.em(this.dbName).createQuery(builder.toString());
        final int indexOfFirstParam = builder.indexOf(PARAM_ONE);
        final int indexOfSecondParam = builder.indexOf(PARAM_TWO);
        if (filter.getKeywords() != null && !EMPTY_STRING.equals(filter.getKeywords()) && (indexOfFirstParam != -1 || indexOfSecondParam != -1)) {
            if (indexOfFirstParam != -1) {
                query.setParameter(1, PERCENT_SYMBOL + filter.getKeywords().toLowerCase() + PERCENT_SYMBOL);
            }
            if (indexOfSecondParam != -1) {
                query.setParameter(2, Long.valueOf(filter.getKeywords()));
            }
        }
        if (!filter.isDisablePagination()) {
            query.setFirstResult(filter.getOffset());
            query.setMaxResults(filter.getSize());
        }
        List resultList = query.getResultList();
        if (filter.getColumnsArray() != null) {
            resultList = this.bindBusinessObjectList(filter.getColumnsArray(), resultList);
        }
        return resultList;
    }
    @Override
    public Long count(final List<String> searchFields, final String keywords, final String where) {
        final StringBuilder builder = new StringBuilder(SELECT_COUNT_QUERY);
        this.buildSelectColumns(null, builder);
        this.buildWhereClause(searchFields, keywords, where, builder);
        final Query query = JPA.em(this.dbName).createQuery(builder.toString());
        final int indexOfFirstParam = builder.indexOf(PARAM_ONE);
        final int indexOfSecondParam = builder.indexOf(PARAM_TWO);
        if (keywords != null && !EMPTY_STRING.equals(keywords) && (indexOfFirstParam != -1 || indexOfSecondParam != -1)) {
            if (indexOfFirstParam != -1) {
                query.setParameter(1, PERCENT_SYMBOL + keywords.toLowerCase() + PERCENT_SYMBOL);
            }
            if (indexOfSecondParam != -1) {
                query.setParameter(2, Long.valueOf(keywords));
            }
        }
        return Long.decode(query.getSingleResult().toString());
    }
    public StringBuilder buildQuery(final String[] columnsArray, final String orderBy, final String order, final List<String> searchFields, final String keywords, final String where) {
        final StringBuilder builder = new StringBuilder(EMPTY_STRING);
        this.buildSelectColumns(columnsArray, builder);
        this.buildWhereClause(searchFields, keywords, where, builder);
        CustomJPAModelLoader.buildOrderBy(orderBy, order, builder);
        return builder;
    }
    protected List<Model> bindBusinessObjectList(final String[] columnsArray, final List resultList) throws SystemException {
        try {
            final List<Model> businessObjectList = new ArrayList<Model>();
            for (Object resultObject : resultList) {
                if (!resultObject.getClass().isArray()) {
                    resultObject = new Object[] {
                            resultObject
                    };
                }
                final Constructor<?> constructor = this.clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                final Model object = (Model) constructor.newInstance();
                final Object[] resultArray = (Object[]) resultObject;
                this.bindObject(columnsArray, object, resultArray);
                businessObjectList.add(object);
            }
            return businessObjectList;
        } catch (final Exception e) {
            throw new SystemException(e);
        }
    }
    private void buildSelectColumns(final String[] columnsArray, final StringBuilder builder) {
        if (columnsArray != null && columnsArray.length > 0) {
            // The array must be sorted to binary search work properly
            Arrays.sort(columnsArray);
            builder.append(SELECT);
            for (int i = 0; i < columnsArray.length; i++) {
                builder.append(columnsArray[i]);
                if (i < columnsArray.length - 1) {
                    builder.append(COMMA);
                }
            }
            builder.append(SPACE_STRING);
        }
        builder.append(FROM).append(this.clazz.getName());
    }
    private void buildWhereClause(final List<String> searchFields, final String keywords, final String where, final StringBuilder builder) {
        if (keywords != null && !EMPTY_STRING.equals(keywords)) {
            final String searchQuery = this.searchQuery(searchFields);
            if (!EMPTY_STRING.equals(searchQuery)) {
                builder.append(WHERE2).append(searchQuery).append(")");
            }
            builder.append(where != null ? AND + where : EMPTY_STRING);
        } else {
            builder.append(where != null ? WHERE + where : EMPTY_STRING);
        }
    }
    private static void buildOrderBy(final String orderBy, final String order, final StringBuilder builder) {
        String orderColumn = orderBy;
        String sort = order;
        if (orderColumn == null && sort == null) {
            orderColumn = ID;
            sort = ASC;
        }
        if (orderColumn == null && sort != null) {
            orderColumn = ID;
        }
        if (sort == null || !sort.toUpperCase().trim().startsWith(ASC) && !sort.toUpperCase().trim().startsWith(DESC)) {
            sort = ASC;
        }
        builder.append(ORDER_BY).append(orderColumn).append(SPACE_STRING).append(sort);
    }
    private void bindObject(final String[] columnsArray, final Model object, final Object[] resultArray)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, SystemException {
        String relationshipPrefix = NULL_STRING;
        for (int i = 0; i < resultArray.length; i++) {
            if (columnsArray[i].contains(DOT)) {
                if (columnsArray[i].startsWith(relationshipPrefix)) {
                    continue;
                }
                final String[] split = columnsArray[i].split("\\.");
                relationshipPrefix = split[0] + DOT;
                final String relationshipGetMethodName = CustomJPAModelLoader.GET_PREFIX + StringUtil.capitalize(split[0]);
                final Method getRelationshipMethod = this.clazz.getDeclaredMethod(relationshipGetMethodName);
                final Class<?> relationshipType = getRelationshipMethod.getReturnType();
                final Constructor<?> relationshipConstructor = relationshipType.getDeclaredConstructor();
                relationshipConstructor.setAccessible(true);
                final Object relationshipObject = relationshipConstructor.newInstance();
                final List<Method> lstRelationshipSetMethods = this.loadRelationshipSetMethods(relationshipType);
                CustomJPAModelLoader.bindRelationshipProperties(columnsArray, resultArray, split, lstRelationshipSetMethods, relationshipObject);
                CustomJPAModelLoader.bindRelationshipObject(object, split, relationshipObject);
                CustomJPAModelLoader.bindObjectProperty(object, columnsArray, resultArray, i);
            } else {
                CustomJPAModelLoader.bindObjectProperty(object, columnsArray, resultArray, i);
            }
        }
    }
    private static void bindObjectProperty(final Model object, final String[] columnsArray, final Object[] resultArray, final int propertyIndex) throws SystemException {
        try {
            final String setMethodName = CustomJPAModelLoader.SET_PREFIX + StringUtil.capitalize(columnsArray[propertyIndex]);
            final Statement statement = new Statement(object, setMethodName, new Object[] {
                    resultArray[propertyIndex]
            });
            statement.execute();
        } catch (final Exception e) {
            throw new SystemException(e);
        }
    }
    private static void bindRelationshipObject(final Model object, final String[] split, final Object relationshipObject) throws SystemException {
        try {
            final String setMethodName = CustomJPAModelLoader.SET_PREFIX + StringUtil.capitalize(split[0]);
            new Statement(object, setMethodName, new Object[] {
                    relationshipObject
            }).execute();
        } catch (final Exception e) {
            throw new SystemException(e);
        }
    }
    private static void bindRelationshipProperties(final String[] columnsArray, final Object[] resultArray, final String[] split, final List<Method> lstRelationshipSetMethods,
            final Object relationshipObject) throws InvocationTargetException, IllegalAccessException {
        for (final Method setMethod : lstRelationshipSetMethods) {
            final String propertyName = split[0] + DOT + StringUtil.uncapitalize(setMethod.getName().replace(CustomJPAModelLoader.SET_PREFIX, EMPTY_STRING));
            final int indexOfColumn = Arrays.binarySearch(columnsArray, propertyName);
            if (indexOfColumn >= 0) {
                setMethod.invoke(relationshipObject, resultArray[indexOfColumn]);
                /*
                 * Outra forma efetiva de invocar: MethodUtil.invoke(setMethod,
                 * ...: relationshipObject, new Object[]
                 * ...: { resultArray[indexOfColumn] })
                 */
            }
        }
    }
    private List<Method> loadRelationshipSetMethods(final Class<?> relationshipType) {
        final List<Method> lstRelationshipSetMethods = new ArrayList<Method>();
        for (final Method method : relationshipType.getDeclaredMethods()) {
            if (CustomJPAModelLoader.isSetter(method)) {
                lstRelationshipSetMethods.add(method);
            }
        }
        return lstRelationshipSetMethods;
    }
    private static boolean isSetter(final Method method) {
        final boolean isAnnotationPresent = method.isAnnotationPresent(PlayPropertyAccessor.class);
        final boolean startsWithSet = method.getName().startsWith(CustomJPAModelLoader.SET_PREFIX);
        final boolean isNativeOrStaticMethod = (method.getModifiers() & NOT_ACCESSIBLE_METHOD) == 0;
        final boolean hasOneParameterType = method.getParameterTypes().length == 1;
        return !isAnnotationPresent && CustomJPAModelLoader.hasSetterNamePattern(startsWithSet, hasOneParameterType, method) && isNativeOrStaticMethod;
    }
    private static boolean hasSetterNamePattern(final boolean startsWithSet, final boolean hasOneParameterType, final Method method) {
        return startsWithSet && hasOneParameterType && method.getName().length() > 3;
    }
    private String searchQuery(final List<String> searchFields) {
        final StringBuilder builder = new StringBuilder(EMPTY_STRING);
        for (final Model.Property property : this.listProperties()) {
            // Enable filters on relations properties
            if (property.isRelation) {
                property.isSearchable = true;
            }
            if (property.isSearchable) {
                if ((searchFields == null || searchFields.isEmpty() ? true : searchFields.contains(property.name)) || this.isSearchableRelationField(searchFields, property)) {
                    this.addOrClause(builder, property);
                    if (!property.isRelation) {
                        continue;
                    }
                    searchFields.remove(property.name);
                    property.name = this.getRelationObjectName(property);
                    while (this.getRalationField(searchFields, property) != null) {
                        this.addOrClause(builder, property);
                        searchFields.remove(property.name);
                        property.name = this.getRelationObjectName(property);
                    }
                }
            }
        }
        return builder.toString();
    }
    private String getRelationObjectName(final Model.Property property) {
        final int indexOf = property.name.indexOf(DOT);
        if (indexOf > 0) {
            return property.name.substring(0, indexOf);
        }
        return property.name;
    }
    private void addOrClause(final StringBuilder builder, final Model.Property property) {
        this.addOrClause(builder);
        if (String.class == property.type || property.name.contains(DOT)) {
            builder.append("LOWER(").append(property.name).append(") LIKE LOWER(?1)");
        } else if (property.type.getName().startsWith("java.lang.")) {
            builder.append(property.name).append(" = ?2 ");
        }
    }
    private void addOrClause(final StringBuilder builder) {
        if (!EMPTY_STRING.equals(builder.toString())) {
            builder.append(OR);
        }
    }
    private boolean isSearchableRelationField(final List<String> searchFields, final Model.Property property) {
        final String ralationField = this.getRalationField(searchFields, property);
        return StringUtil.isNotEmpty(ralationField);
    }
    private String getRalationField(final List<String> searchFields, final Model.Property property) {
        String relationField = null;
        for (final String element : searchFields) {
            if (element.contains(property.name + DOT)) {
                property.name = element;
                relationField = element;
            }
        }
        return relationField;
    }

    /**
     * @author jgomes | 27/11/2014 - 11:41:30
     */
    public static class CustomJPAModelFilter {

        private int offset;
        private int size;
        private String[] columnsArray;
        private String orderBy;
        private String order;
        private String searchFields;
        private List<String> lstSearchFields;
        private String keywords;
        private String where;
        private boolean disablePagination = false;

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Constructors.
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        public CustomJPAModelFilter(final String searchFields, final String keywords, final String where) {
            super();
            this.searchFields = searchFields;
            this.lstSearchFields = searchFields == null ? new LinkedList() : new LinkedList(Arrays.asList(searchFields.split("[ ]")));
            this.keywords = keywords;
            this.where = where;
        }
        public CustomJPAModelFilter(final String[] columnsArray, final String searchFields, final String keywords, final String where, final String orderBy, final String order) {
            super();
            this.columnsArray = columnsArray;
            this.searchFields = searchFields;
            this.lstSearchFields = searchFields == null ? new LinkedList() : new LinkedList(Arrays.asList(searchFields.split("[ ]")));
            this.keywords = keywords;
            this.where = where;
            this.orderBy = orderBy;
            this.order = order;
        }
        public CustomJPAModelFilter(
                final String[] columnsArray, final String searchFields, final String keywords, final String where, final String orderBy, final String order, final boolean disablePagination) {
            super();
            this.columnsArray = columnsArray;
            this.searchFields = searchFields;
            this.lstSearchFields = searchFields == null ? new LinkedList() : new LinkedList(Arrays.asList(searchFields.split("[ ]")));
            this.keywords = keywords;
            this.where = where;
            this.orderBy = orderBy;
            this.order = order;
            this.disablePagination = disablePagination;
        }
        public CustomJPAModelFilter(final String[] columnsArray, final String searchFields, final String keywords, final String where, final String orderBy, final String order, final int pageSize) {
            super();
            this.columnsArray = columnsArray;
            this.searchFields = searchFields;
            this.lstSearchFields = searchFields == null ? new LinkedList() : new LinkedList(Arrays.asList(searchFields.split("[ ]")));
            this.keywords = keywords;
            this.where = where;
            this.orderBy = orderBy;
            this.order = order;
            this.size = pageSize;
        }
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Get/Set.
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        public int getOffset() {
            return this.offset;
        }
        public void setOffset(final int offset) {
            this.offset = offset;
        }
        public int getSize() {
            return this.size;
        }
        public void setSize(final int size) {
            this.size = size;
        }
        public String[] getColumnsArray() {
            return this.columnsArray;
        }
        public void setColumnsArray(final String[] columnsArray) {
            this.columnsArray = columnsArray;
        }
        public String getOrderBy() {
            return this.orderBy;
        }
        public void setOrderBy(final String orderBy) {
            this.orderBy = orderBy;
        }
        public String getOrder() {
            return this.order;
        }
        public void setOrder(final String order) {
            this.order = order;
        }
        public String getSearchFields() {
            return this.searchFields;
        }
        public void setSearchFields(final String searchFields) {
            this.searchFields = searchFields;
        }
        public List<String> getLstSearchFields() {
            return this.lstSearchFields;
        }
        public void setLstSearchFields(final List<String> lstSearchFields) {
            this.lstSearchFields = lstSearchFields;
        }
        public String getKeywords() {
            return this.keywords;
        }
        public void setKeywords(final String keywords) {
            this.keywords = keywords;
        }
        public String getWhere() {
            return this.where;
        }
        public void setWhere(final String where) {
            this.where = where;
        }
        public boolean isDisablePagination() {
            return this.disablePagination;
        }
        public void setDisablePagination(final boolean disablePagination) {
            this.disablePagination = disablePagination;
        }
    }
}
