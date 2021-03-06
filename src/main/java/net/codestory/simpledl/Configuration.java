/**
 * Copyright (C) 2013-2016 Frédéric Camblor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package net.codestory.simpledl;

public enum Configuration {
  // Standard system properties
  USER_HOME("user.home", null, true),
  OS_NAME("os.name", null, true);

  private final String key;
  private final String defaultValue;
  private final boolean required;

  Configuration(String key, String defaultValue, boolean required) {
    this.key = key;
    this.defaultValue = defaultValue;
    this.required = required;
  }

  public int getInt() {
    return Integer.parseInt(get());
  }

  public String get() {
    return get(key, defaultValue, required);
  }

  public static String get(String key, String defaultValue, boolean required) {
    String value = System.getProperty(key);

    if ((value == null) || value.trim().isEmpty()) {
      if (required) {
        throw new IllegalArgumentException("System property [" + key + "] cannot be null nor empty");
      }
      return defaultValue;
    }

    return value;
  }
}
