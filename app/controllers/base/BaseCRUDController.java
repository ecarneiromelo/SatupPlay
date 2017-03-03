package controllers.base;

import static common.constants.ControllerConstants.BLANK_HTML;
import static common.constants.ControllerConstants.CAPTCHA_COLOR;
import static common.constants.ControllerConstants.CAPTCHA_EXPIRATION;
import static common.constants.ControllerConstants.CRUD_BATCH_DELETED_MESSAGE;
import static common.constants.ControllerConstants.CRUD_BATCH_DELETE_ERROR_MESSAGE;
import static common.constants.ControllerConstants.CRUD_BLANK_HTML;
import static common.constants.ControllerConstants.CRUD_CREATED_MESSAGE;
import static common.constants.ControllerConstants.CRUD_DELETED_MESSAGE;
import static common.constants.ControllerConstants.CRUD_DELETE_ERROR_MESSAGE;
import static common.constants.ControllerConstants.CRUD_HAS_ERRORS;
import static common.constants.ControllerConstants.CRUD_LIST_HTML;
import static common.constants.ControllerConstants.CRUD_MAIN_PATH;
import static common.constants.ControllerConstants.CRUD_SAVED_MESSAGE;
import static common.constants.ControllerConstants.CRUD_SHOW_HTML;
import static common.constants.ControllerConstants.DOT;
import static common.constants.ControllerConstants.DOT_BLANK;
import static common.constants.ControllerConstants.DOT_LIST;
import static common.constants.ControllerConstants.DOT_SHOW;
import static common.constants.ControllerConstants.ERROR;
import static common.constants.ControllerConstants.HIDDEN;
import static common.constants.ControllerConstants.LONGTEXT;
import static common.constants.ControllerConstants.OBJECT;
import static common.constants.ControllerConstants.PAGE;
import static common.constants.ControllerConstants.PASSWD;
import static common.constants.ControllerConstants.SAVE;
import static common.constants.ControllerConstants.SAVE_AND_ADD_ANOTHER;
import static common.constants.ControllerConstants.SHOW_HTML;
import static common.constants.ControllerConstants.SLASH;
import static common.constants.ControllerConstants.TEXT;
import static common.constants.ControllerConstants.WHERE;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.PersistenceException;

import common.annotations.FieldOrder;
import common.exceptions.SystemException;
import common.jpa.CustomJPAModelLoader;
import common.jpa.CustomJPAModelLoader.CustomJPAModelFilter;
import common.utils.PlayUtil;
import controllers.CRUD;
import play.Logger;
import play.cache.Cache;
import play.data.binding.Binder;
import play.data.validation.MaxSize;
import play.data.validation.Password;
import play.data.validation.Validation;
import play.db.Model;
import play.db.Model.Property;
import play.exceptions.TemplateNotFoundException;
import play.i18n.Messages;
import play.libs.Images;
import play.mvc.results.Error;

/**
 * This class change the default CRUD module’s behaviour
 *
 * @author jgomes
 */
public abstract class BaseCRUDController extends CRUD {

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Public static methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static void list(final int page, final String search, final String searchFields, final String orderBy, final String order) {
        final CustomObjectType type = (CustomObjectType) ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        int pageParam = page;
        if (pageParam < 1) {
            pageParam = 1;
        }
        final String where = (String) request.args.get(WHERE);
        final List<Model> objects = type.findPage(pageParam, new CustomJPAModelFilter(null, searchFields, search, where, orderBy, order));
        final Long count = type.count(new CustomJPAModelFilter(searchFields, search, where));
        final Long totalCount = type.count(null, null, where);
        renderArgs.put(PAGE, pageParam);
        try {
            render(type, objects, count, totalCount, orderBy, order);
        } catch (final TemplateNotFoundException e) {
            render(CRUD_LIST_HTML, type, objects, count, totalCount, orderBy, order);
        }
    }
    public static void blank() throws SystemException {
        final ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Model object = null;
        try {
            final Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            object = (Model) constructor.newInstance();
            render(type, object);
        } catch (final TemplateNotFoundException e) {
            render(CRUD_BLANK_HTML, type, object);
        } catch (final Exception e) {
            throw new SystemException(e);
        }
    }
    public static void show(final String id) throws SystemException {
        final ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Model object = null;
        try {
            object = type.findById(id);
            notFoundIfNull(object);
            render(type, object);
        } catch (final TemplateNotFoundException e) {
            render(CRUD_SHOW_HTML, type, object);
        } catch (final Exception e) {
            throw new SystemException(e);
        }
    }
    public static void create() throws SystemException {
        try {
            final ObjectType type = ObjectType.get(getControllerClass());
            notFoundIfNull(type);
            final Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            final Model object = (Model) constructor.newInstance();
            Binder.bindBean(params.getRootParamNode(), OBJECT, object);
            validation.valid(object);
            renderIfHasErrors(type, object, BLANK_HTML, true);
            object._save();
            flash.success(Messages.get(CRUD_CREATED_MESSAGE, type.modelName));
            redirect(object);
        } catch (final Exception e) {
            throw new SystemException(e);
        }
    }
    public static void save(final String id) throws SystemException {
        try {
            final ObjectType type = ObjectType.get(getControllerClass());
            notFoundIfNull(type);
            final Model object = type.findById(id);
            notFoundIfNull(object);
            Binder.bindBean(params.getRootParamNode(), OBJECT, object);
            validation.valid(object);
            renderIfHasErrors(type, object, SHOW_HTML, true);
            object._save();
            flash.success(Messages.get(CRUD_SAVED_MESSAGE, type.modelName));
            redirect(object);
        } catch (final Exception e) {
            throw new SystemException(e);
        }
    }
    public static void delete(final String id) throws SystemException {
        final ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Model object;
        try {
            object = type.findById(id);
            notFoundIfNull(object);
        } catch (final Exception e) {
            throw new SystemException(e);
        }
        try {
            object._delete();
            flash.success(Messages.get(CRUD_DELETED_MESSAGE, type.modelName));
        } catch (final PersistenceException e) {
            flash.error(Messages.get(CRUD_DELETE_ERROR_MESSAGE, type.modelName));
        }
        redirect(request.controller + DOT_LIST);
    }
    public static void batchDelete(final String[] arrayIds) throws Exception {
        final ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        try {
            for (int i = 0; i < arrayIds.length; i++) {
                final Model object = type.findById(arrayIds[i]);
                notFoundIfNull(object);
                object._delete();
            }
        } catch (final Exception e) {
            flash.error(Messages.get(CRUD_BATCH_DELETE_ERROR_MESSAGE));
            redirect(request.controller + DOT_LIST);
        }
        flash.success(Messages.get(CRUD_BATCH_DELETED_MESSAGE, type.modelName));
        redirect(request.controller + DOT_LIST);
    }
    public static void renderCaptcha(final String captchaUuid) throws IOException {
        try (
             final Images.Captcha captcha = Images.captcha();) {
            final String code = captcha.getText(CAPTCHA_COLOR);
            Cache.set(captchaUuid, code, CAPTCHA_EXPIRATION);
            captcha.close();
            renderBinary(captcha);
        }
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Protected static methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    protected static void renderIfHasErrors(final ObjectType type, final Model object, final String crudPage, final boolean useDefaultErrorMessage) {
        checkErrors(type, object, crudPage, useDefaultErrorMessage);
    }
    protected static void renderIfHasErrors(final ObjectType type, final Object object, final String crudPage, final boolean useDefaultErrorMessage) {
        checkErrors(type, object, crudPage, useDefaultErrorMessage);
    }
    protected static void redirect(final Model object) {
        if (params.get(SAVE) != null) {
            redirect(request.controller + DOT_LIST);
        }
        if (params.get(SAVE_AND_ADD_ANOTHER) != null) {
            redirect(request.controller + DOT_BLANK);
        }
        redirect(request.controller + DOT_SHOW, object._key());
    }
    protected static ObjectType createObjectType(final Class<? extends Model> entityClass) {
        return new CustomObjectType(entityClass);
    }
 
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static void checkErrors(final ObjectType type, final Object object, final String crudPage, final boolean useDefaultErrorMessage) {
        if (Validation.hasErrors() || renderArgs.get(ERROR) != null) {
            if (useDefaultErrorMessage) {
                renderArgs.put(ERROR, Messages.get(CRUD_HAS_ERRORS));
            }
            try {
                render(request.controller.replace(DOT, SLASH) + SLASH + crudPage, type, object);
            } catch (final TemplateNotFoundException e) {
                render(CRUD_MAIN_PATH + crudPage, type, object);
            }
        }
    }

    /**
     * This inner class override de
     * <code>controllers.CRUD.ObjectType#getFields()</code> to sort the fields
     * according to the annotation @FieldOrder
     *
     * @author jgomes | 18/11/2014 - 19:49:13
     */
    public static class CustomObjectType extends ObjectType {

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Constructors.
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        public CustomObjectType(final Class<? extends Model> modelClass) {
            super(modelClass);
        }
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // * @see controllers.CRUD.ObjectType#getFields()
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        @Override
        public List<ObjectField> getFields() {
            final List<CustomObjectField> fields = new ArrayList<>();
            final List<ObjectField> hiddenFields = new ArrayList<>();
            for (final Model.Property f : this.factory.listProperties()) {
                final CustomObjectField of = new CustomObjectField(f);
                if (of.type != null) {
                    if (of.type.equals(HIDDEN)) {
                        hiddenFields.add(of);
                    } else {
                        fields.add(of);
                    }
                }
            }
            Collections.sort(fields);
            hiddenFields.addAll(fields);
            return hiddenFields;
        }
        public List<Model> findAll(final String orderBy, final String order) {
            return new CustomJPAModelLoader(super.entityClass).findAll(orderBy, order);
        }
        public Long count(final CustomJPAModelFilter filter) {
            return new CustomJPAModelLoader(super.entityClass).count(filter.getLstSearchFields(), filter.getKeywords(), filter.getWhere());
        }
        public List<Model> findPage(final int page, final CustomJPAModelFilter filter) {
            try {
                if (!filter.isDisablePagination()) {
                    if (filter.getSize() == 0) {
                        final int pageSize = PlayUtil.getPageSize();
                        filter.setSize(pageSize);
                        filter.setOffset((page - 1) * pageSize);
                    } else {
                        filter.setOffset((page - 1) * filter.getSize());
                    }
                }
                return new CustomJPAModelLoader(super.entityClass).fetch(filter);
            } catch (final SystemException e) {
                Logger.error(e, "Did you forgoten to specify relation fields on \"searchFields\" property?");
                throw new Error(e.toString());
            }
        }
        public StringBuilder buildQuery(final String[] columnsArray, final String search, final String searchFields, final String orderBy, final String order, final String where) {
            final List<String> properties = searchFields == null ? new ArrayList(0) : Arrays.asList(searchFields.split("[ ]"));
            return new CustomJPAModelLoader(super.entityClass).buildQuery(columnsArray, orderBy, order, properties, search, where);
        }

        /**
         * This class change the default
         * <code>controllers.CRUD.ObjectType.ObjectField</code> behaviour
         * to sort the fields according to the annotation @FieldOrder
         *
         * @author jgomes | 18/11/2014 - 19:49:31
         */
        public static class CustomObjectField extends ObjectType.ObjectField implements Comparable<Object> {

            private String typeName = "unknown";
            private final Model.Property property;

            // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            // Constructors.
            // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            public CustomObjectField(final Property property) {
                super(property);
                this.property = property;
                this.typeName = property.type.getSimpleName().toString();
                final Field field = property.field;
                if (CharSequence.class.isAssignableFrom(field.getType())) {
                    this.type = TEXT;
                    if (field.isAnnotationPresent(MaxSize.class)) {
                        final int maxSize = field.getAnnotation(MaxSize.class).value();
                        if (maxSize > 255) {
                            this.type = LONGTEXT;
                        }
                    }
                    if (field.isAnnotationPresent(Password.class)) {
                        this.type = PASSWD;
                    }
                }
            }
            // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            // * @see
            // controllers.CRUD.ObjectType.ObjectField#compareTo(java.lang.Object)
            // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            @Override
            public int compareTo(final Object object) {
                final CustomObjectField other = (CustomObjectField) object;
                Short thisColumnOrder = -1;
                Short otherColumnOrder = 1;
                if (this.property.field.isAnnotationPresent(FieldOrder.class)) {
                    thisColumnOrder = this.property.field.getAnnotation(FieldOrder.class).value();
                }
                if (other.property.field.isAnnotationPresent(FieldOrder.class)) {
                    otherColumnOrder = other.property.field.getAnnotation(FieldOrder.class).value();
                }
                return thisColumnOrder.compareTo(otherColumnOrder);
            }
            // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            // * @see java.lang.Object#hashCode()
            // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            @Override
            public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + (this.property == null ? 0 : this.property.hashCode());
                return result;
            }
            // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            // * @see java.lang.Object#equals(java.lang.Object)
            // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            @Override
            public boolean equals(final Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || !(obj instanceof CustomObjectField)) {
                    return false;
                }
                final CustomObjectField other = (CustomObjectField) obj;
                if (this.property == null && other.property != null) {
                    return false;
                }
                return this.property.equals(other.property);
            }
            // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            // Get
            // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            public String typeName() {
                return this.typeName;
            }
        }
    }
}
