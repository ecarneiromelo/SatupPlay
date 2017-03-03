package common.encrypt;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import common.exceptions.SystemException;
import common.utils.BinaryUtil;
import play.Logger;

/**
 * @author jgomes
 */
public final class EncryptProvider {

    private static final int ITERATION_COUNT = 10000;

    public static String encryptMD5(final String data) throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance(AlgorithmName.MD5.getValue());
        final BigInteger hash = new BigInteger(1, md.digest(data.getBytes()));
        return hash.toString(16);
    }
    public static byte[] encryptMD5(final byte[] data) throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance(AlgorithmName.MD5.getValue());
        final BigInteger hash = new BigInteger(1, md.digest(data));
        return hash.toByteArray();
    }
    public static String encryptMD5(final String data, final boolean formatHex) throws NoSuchAlgorithmException {
        if (formatHex) {
            return BinaryUtil.encodeHexString(encryptMD5(data.getBytes())).toUpperCase();
        }
        return encryptMD5(data);
    }
    public static byte[] encryptDES(final byte[] key, final byte[] data) throws SystemException {
        return encrypt(key, data, AlgorithmName.DES);
    }
    public static String encryptDES(final String key, final String data) throws SystemException {
        final byte[] encrypted = encrypt(key.getBytes(), data.getBytes(), AlgorithmName.DES);
        return Base64.encodeBase64String(encrypted);
    }
    public static String encryptDES(final String key, final String data, final boolean formatHex) throws SystemException {
        final byte[] encrypted = encrypt(key.getBytes(), data.getBytes(), AlgorithmName.DES);
        if (formatHex) {
            return BinaryUtil.encodeHexString(encrypted).toUpperCase();
        }
        return Base64.encodeBase64String(encrypted);
    }
    public static byte[] decryptDES(final byte[] key, final byte[] data) throws SystemException {
        return decrypt(key, data, AlgorithmName.DES);
    }
    public static String decryptDES(final String key, final String data) throws SystemException {
        final byte[] cleanDataByteArray = Base64.decodeBase64(data);
        final byte[] decrypted = decrypt(key.getBytes(), cleanDataByteArray, AlgorithmName.DES);
        return new String(decrypted);
    }
    public static byte[] encryptDESede(final byte[] key, final byte[] data) throws SystemException {
        return encrypt(key, data, AlgorithmName.DESEDE);
    }
    public static String encryptDESede(final String key, final String data) throws SystemException {
        final byte[] encrypted = encrypt(key.getBytes(), data.getBytes(), AlgorithmName.DESEDE);
        return Base64.encodeBase64String(encrypted);
    }
    public static String encryptDESede(final String key, final String data, final boolean formatHex) throws SystemException {
        final byte[] encrypted = encrypt(key.getBytes(), data.getBytes(), AlgorithmName.DESEDE);
        if (formatHex) {
            return BinaryUtil.encodeHexString(encrypted).toUpperCase();
        }
        return Base64.encodeBase64String(encrypted);
    }
    public static byte[] decryptDESede(final byte[] key, final byte[] data) throws SystemException {
        return decrypt(key, data, AlgorithmName.DESEDE);
    }
    public static String decryptDESede(final String key, final String data) throws SystemException {
        final byte[] cleanDataByteArray = Base64.decodeBase64(data);
        final byte[] decrypted = decrypt(key.getBytes(), cleanDataByteArray, AlgorithmName.DESEDE);
        return new String(decrypted);
    }
    public static byte[] encryptDESecbPkcs5Padding(final byte[] key, final byte[] data) throws SystemException {
        return encrypt(key, data, AlgorithmName.DES_ECB_PKCS5_PADDING);
    }
    public static String encryptDESecbPkcs5Padding(final String key, final String data) throws SystemException {
        final byte[] encrypted = encrypt(key.getBytes(), data.getBytes(), AlgorithmName.DES_ECB_PKCS5_PADDING);
        return Base64.encodeBase64String(encrypted);
    }
    public static String encryptDESecbPkcs5Padding(final String key, final String data, final boolean formatHex) throws SystemException {
        final byte[] encrypted = encrypt(key.getBytes(), data.getBytes(), AlgorithmName.DES_ECB_PKCS5_PADDING);
        if (formatHex) {
            return BinaryUtil.encodeHexString(encrypted).toUpperCase();
        }
        return Base64.encodeBase64String(encrypted);
    }
    public static byte[] decryptDESecbPkcs5Padding(final byte[] key, final byte[] data) throws SystemException {
        return decrypt(key, data, AlgorithmName.DES_ECB_PKCS5_PADDING);
    }
    public static String decryptDESecbPkcs5Padding(final String key, final String data) throws SystemException {
        final byte[] cleanDataByteArray = Base64.decodeBase64(data);
        final byte[] decrypted = decrypt(key.getBytes(), cleanDataByteArray, AlgorithmName.DES_ECB_PKCS5_PADDING);
        return new String(decrypted);
    }
    public static byte[] encryptHmacSHA256(final byte[] key, final byte[] data) throws SystemException {
        return encrypt(key, data, AlgorithmName.HMAC_SHA256);
    }
    public static String encryptHmacSHA256(final String key, final String data) throws SystemException {
        final byte[] encrypted = encrypt(key.getBytes(), data.getBytes(), AlgorithmName.HMAC_SHA256);
        return Base64.encodeBase64String(encrypted);
    }
    public static String encryptHmacSHA256(final String key, final String data, final boolean formatHex) throws SystemException {
        final byte[] encrypted = encrypt(key.getBytes(), data.getBytes(), AlgorithmName.HMAC_SHA256);
        if (formatHex) {
            return BinaryUtil.encodeHexString(encrypted).toUpperCase();
        }
        return Base64.encodeBase64String(encrypted);
    }
    public static byte[] encryptAES(final byte[] key, final byte[] data) throws SystemException {
        return encrypt(key, data, AlgorithmName.AES);
    }
    public static String encryptAES(final String key, final String data) throws SystemException {
        final byte[] encrypted = encrypt(key.getBytes(), data.getBytes(), AlgorithmName.AES);
        return Base64.encodeBase64String(encrypted);
    }
    public static String encryptAES(final String key, final String data, final boolean formatHex) throws SystemException {
        final byte[] encrypted = encrypt(key.getBytes(), data.getBytes(), AlgorithmName.AES);
        if (formatHex) {
            return BinaryUtil.encodeHexString(encrypted).toUpperCase();
        }
        return Base64.encodeBase64String(encrypted);
    }
    public static byte[] decryptAES(final byte[] key, final byte[] data) throws SystemException {
        return decrypt(key, data, AlgorithmName.AES);
    }
    public static String decryptAES(final String key, final String data) throws SystemException {
        final byte[] cleanDataByteArray = Base64.decodeBase64(data);
        final byte[] decrypted = decrypt(key.getBytes(), cleanDataByteArray, AlgorithmName.AES);
        return new String(decrypted);
    }
    public static byte[] encryptAESlikeARMcryptoLib(final byte[] key, final byte[] cleanData) throws SystemException {
        return encrypt(key, cleanData, AlgorithmName.AES_ARM_CRYPTO_LIB);
    }
    public static byte[] encryptAESlikeARMcryptoLib(final byte[] key, final byte[] cleanData, final int cipherDataLength) throws SystemException {
        byte[] encrypted = encrypt(key, cleanData, AlgorithmName.AES_ARM_CRYPTO_LIB);
        encrypted = Arrays.copyOf(encrypted, cipherDataLength);
        return encrypted;
    }
    public static byte[] encrypt(final byte[] key, final byte[] data, final AlgorithmName algorithm) throws SystemException {
        try {
            Cipher cipher = null;
            KeySpec keySpec = null;
            SecretKeyFactory secretkeyFactory = null;
            Mac macInstance = null;
            switch (algorithm) {
                case DES:
                    keySpec = new DESKeySpec(key);
                    secretkeyFactory = SecretKeyFactory.getInstance(algorithm.getValue());
                    cipher = configEncryptCipher(algorithm, keySpec, secretkeyFactory);
                    break;
                case DES_ECB_PKCS5_PADDING:
                    keySpec = new DESKeySpec(key);
                    secretkeyFactory = SecretKeyFactory.getInstance(AlgorithmName.DES.getValue());
                    cipher = configEncryptCipher(algorithm, keySpec, secretkeyFactory);
                    break;
                case DESEDE:
                    keySpec = new DESedeKeySpec(key);
                    secretkeyFactory = SecretKeyFactory.getInstance(algorithm.getValue());
                    cipher = configEncryptCipher(algorithm, keySpec, secretkeyFactory);
                    break;
                case HMAC_SHA256:
                    keySpec = new SecretKeySpec(key, algorithm.getValue());
                    macInstance = Mac.getInstance(algorithm.getValue());
                    macInstance.init((Key) keySpec);
                    return macInstance.doFinal(data);
                case AES:
                    keySpec = new SecretKeySpec(key, algorithm.getValue());
                    cipher = Cipher.getInstance(algorithm.getValue());
                    cipher.init(Cipher.ENCRYPT_MODE, (Key) keySpec);
                    break;
                default:
                    break;
            }
            return cipher.doFinal(data);
        } catch (final Exception e) {
            throw new SystemException(e);
        }
    }
    public static byte[] decrypt(final byte[] key, final byte[] data, final AlgorithmName algorithm) throws SystemException {
        try {
            Cipher cipher = null;
            KeySpec keySpec = null;
            SecretKeyFactory secretkeyFactory = null;
            switch (algorithm) {
                case DES:
                    keySpec = new DESKeySpec(key);
                    secretkeyFactory = SecretKeyFactory.getInstance(algorithm.getValue());
                    cipher = configDecryptCipher(algorithm, keySpec, secretkeyFactory);
                    break;
                case DES_ECB_PKCS5_PADDING:
                    keySpec = new DESKeySpec(key);
                    secretkeyFactory = SecretKeyFactory.getInstance(AlgorithmName.DES.getValue());
                    cipher = configDecryptCipher(algorithm, keySpec, secretkeyFactory);
                    break;
                case DESEDE:
                    keySpec = new DESedeKeySpec(key);
                    secretkeyFactory = SecretKeyFactory.getInstance(algorithm.getValue());
                    cipher = configDecryptCipher(algorithm, keySpec, secretkeyFactory);
                    break;
                case AES:
                    keySpec = new SecretKeySpec(key, algorithm.getValue());
                    cipher = Cipher.getInstance(algorithm.getValue());
                    cipher.init(Cipher.DECRYPT_MODE, (Key) keySpec);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid algorithm for decrypt: " + algorithm.getValue());
            }
            return cipher.doFinal(data);
        } catch (final Exception e) {
            throw new SystemException(e);
        }
    }
    public static String encryptAESWithHmac256(final String value, final String salt) throws SystemException {
        try {
            final SecureRandom secureRandom = SecureRandom.getInstance(AlgorithmName.SHA1PRNG.getValue());
            // Generate 128 bit Salt for Encryption Key
            final byte[] aesSalt = new byte[16];
            secureRandom.nextBytes(aesSalt);
            // Generate 128 bit Encryption Key (AES keys)
            final byte[] aesKey = deriveKey(value, aesSalt, ITERATION_COUNT, 128);
            // Perform Encryption
            final SecretKeySpec secretKeySpec = new SecretKeySpec(aesKey, AlgorithmName.AES.getValue());
            final Cipher cipher = Cipher.getInstance(AlgorithmName.AES_CTR_NO_PADDING.getValue());
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(new byte[16]));
            final byte[] buffer = cipher.doFinal(salt.getBytes(StandardCharsets.UTF_8));
            // Generate 160 bit Salt for HMAC Key
            final byte[] hmacSalt = new byte[20];
            secureRandom.nextBytes(hmacSalt);
            // Generate 160 bit HMAC Key
            final byte[] hmacKey = deriveKey(value, hmacSalt, ITERATION_COUNT, 160);
            // Perform HMAC using SHA-256
            final SecretKeySpec hmacSecretKeySpec = new SecretKeySpec(hmacKey, AlgorithmName.HMAC_SHA256.getValue());
            final Mac mac = Mac.getInstance(AlgorithmName.HMAC_SHA256.getValue());
            mac.init(hmacSecretKeySpec);
            final byte[] hmacResult = mac.doFinal(buffer);
            // Construct Output as "ESALT + HSALT + CIPHERTEXT + HMAC"
            final byte[] output = new byte[36 + buffer.length + 32];
            System.arraycopy(aesSalt, 0, output, 0, 16);
            System.arraycopy(hmacSalt, 0, output, 16, 20);
            System.arraycopy(buffer, 0, output, 36, buffer.length);
            System.arraycopy(hmacResult, 0, output, 36 + buffer.length, 32);
            return Base64.encodeBase64String(output);
        } catch (final NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | NoSuchPaddingException | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException e) {
            throw new SystemException(e);
        }
    }
    public static String decryptAESWithHmac256(final String encryptedString, final String value) {
        // Recover our Byte Array by Base64 Decoding
        final byte[] decodedBuffer = Base64.decodeBase64(encryptedString);
        // Check Minimum Length (ESALT (16) + HSALT (20) + HMAC (32))
        if (decodedBuffer.length > 68) {
            // Recover Elements from String
            final byte[] aesSalt = Arrays.copyOfRange(decodedBuffer, 0, 16);
            final byte[] hmacSalt = Arrays.copyOfRange(decodedBuffer, 16, 36);
            final byte[] buffer = Arrays.copyOfRange(decodedBuffer, 36, decodedBuffer.length - 32);
            final byte[] hmacResult = Arrays.copyOfRange(decodedBuffer, decodedBuffer.length - 32, decodedBuffer.length);
            try {
                // Regenerate HMAC key using Recovered Salt (hsalt)
                final byte[] hmacKey = deriveKey(value, hmacSalt, ITERATION_COUNT, 160);
                // Perform HMAC using SHA-256
                final SecretKeySpec hmacSecretKeySpec = new SecretKeySpec(hmacKey, AlgorithmName.HMAC_SHA256.getValue());
                final Mac mac = Mac.getInstance(AlgorithmName.HMAC_SHA256.getValue());
                mac.init(hmacSecretKeySpec);
                final byte[] hmacRecovered = mac.doFinal(buffer);
                // Compare computed HMAC vs recovered HMAC
                if (MessageDigest.isEqual(hmacResult, hmacRecovered)) {
                    // Regenerate Encryption Key using Recovered Salt (esalt)
                    final byte[] aesKey = deriveKey(value, aesSalt, ITERATION_COUNT, 128);
                    // Perform Decryption
                    final SecretKeySpec aesSecretKeySpec = new SecretKeySpec(aesKey, AlgorithmName.AES.getValue());
                    final Cipher cipher = Cipher.getInstance(AlgorithmName.AES_CTR_NO_PADDING.getValue());
                    cipher.init(Cipher.DECRYPT_MODE, aesSecretKeySpec, new IvParameterSpec(new byte[16]));
                    final byte[] output = cipher.doFinal(buffer);
                    // Return our Decrypted String
                    return new String(output, StandardCharsets.UTF_8);
                }
            } catch (final NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | NoSuchPaddingException | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException e) {
                Logger.error(e, e.getLocalizedMessage());
            }
        }
        return null;
    }
    private static Cipher configEncryptCipher(final AlgorithmName algorithm, final KeySpec keySpec, final SecretKeyFactory secretkeyFactory)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        Cipher cipher;
        cipher = Cipher.getInstance(algorithm.getValue());
        cipher.init(Cipher.ENCRYPT_MODE, secretkeyFactory.generateSecret(keySpec));
        return cipher;
    }
    private static Cipher configDecryptCipher(final AlgorithmName algorithm, final KeySpec keySpec, final SecretKeyFactory secretkeyFactory)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        Cipher cipher;
        cipher = Cipher.getInstance(algorithm.getValue());
        cipher.init(Cipher.DECRYPT_MODE, secretkeyFactory.generateSecret(keySpec));
        return cipher;
    }
    private static byte[] deriveKey(final String value, final byte[] salt, final int iterations, final int keySize) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final PBEKeySpec pbeKeySpec = new PBEKeySpec(value.toCharArray(), salt, iterations, keySize);
        final SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(AlgorithmName.PBKDF2_WITH_HMAC_SHA1.getValue());
        return secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
    }

    /**
     * @author jgomes
     */
    public enum AlgorithmName {
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Constants.
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        MD5("MD5"),
        DES("DES"),
        DESEDE("DESede"),
        DES_ECB_PKCS5_PADDING("DES/ECB/PKCS5Padding"),
        HMAC_SHA256("HmacSHA256"),
        AES("AES"),
        AES_ARM_CRYPTO_LIB("AES/ARM-Crypto-lib"),
        SHA1PRNG("SHA1PRNG"),
        AES_CTR_NO_PADDING("AES/CTR/NoPadding"),
        PBKDF2_WITH_HMAC_SHA1("PBKDF2WithHmacSHA1");

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Attribute.
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        private final String value;

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Constructors.
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        private AlgorithmName(final String code) {
            this.value = code;
        }
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Get Method
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        public String getValue() {
            return this.value;
        }
    }
}