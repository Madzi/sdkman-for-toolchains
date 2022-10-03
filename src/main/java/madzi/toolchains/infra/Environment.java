package madzi.toolchains.infra;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * The environment.
 */
public interface Environment {

    /**
     * Checks the wether of existing provided variable.
     *
     * @param variable the variable
     * @return the value
     */
    boolean hasProperty(Variable variable);

    String getProperty(Variable variable);

    String getProperty(Variable variable, String defaultValue);

    String getRequiredProperty(Variable variable);

    @FunctionalInterface
    interface Variable {
        String key();
    }

    static Builder builder() {
        return new Builder();
    }

    class Builder {
        private final Map<String, String> values;

        private Builder() {
            values = new HashMap<>();
        }

        public Builder with(final Variable variable, final String value) {
            values.put(Objects.requireNonNull(variable, "The environment variable cannot be NULL").key(), value);
            return this;
        }

        public Environment build() {
            return new ApplicationEnvironment(values);
        }
    }

    class ApplicationEnvironment implements Environment {

        private final Map<String, String> usrValues;
        private final Map<String, String> envValues;
        private final Map<String, String> sysValues;

        private ApplicationEnvironment(final Map<String, String> usrValues) {
            this.usrValues = Objects.requireNonNull(usrValues, "The user provided values cannot be NULL");
            this.envValues = asMap(System.getProperties());
            this.sysValues = System.getenv();
        }

        @Override
        public boolean hasProperty(final Variable variable) {
            final String key = Objects.requireNonNull(variable, "The environment variable cannot be NULL").key();
            return usrValues.containsKey(key) || envValues.containsKey(key) || sysValues.containsKey(key);
        }

        @Override
        public String getProperty(final Variable variable) {
            final String key = Objects.requireNonNull(variable, "The environment variable cannot be NULL").key();
            return usrValues.getOrDefault(key, envValues.getOrDefault(key, sysValues.getOrDefault(key, null)));
        }

        @Override
        public String getProperty(final Variable variable, String defaultValue) {
            return hasProperty(variable) ? getProperty(variable) : defaultValue;
        }

        @Override
        public String getRequiredProperty(final Variable variable) {
            if (!hasProperty(variable)) {
                throw new IllegalStateException("Expected environment variable '" + variable.key() + "' doesn't exists");
            }
            return getProperty(variable);
        }

        private static Map<String, String> asMap(final Properties properties) {
            return properties.stringPropertyNames().stream().collect(Collectors.toMap(item -> item, item -> properties.getProperty(item)));
        }
    }
}
