package fr.gamagora.jponzo.rtrace4j.utils.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Centralization of Java reflection helpers
 * @author jponzo
 *
 */
public class ReflectionUtils {
	/**
	 * Find all fields defined in whole hierarchy of given type
	 * @param type the given type
	 * @return the fields and super fields of the given type
	 */
	public static List<Field> retieveAllFields(Class<?> type) {
		List<Field> fields = new ArrayList<Field>(Arrays.asList(type.getDeclaredFields()));

		if (type.getSuperclass() != null) {
			List<Field> superFields = retieveAllFields(type.getSuperclass());
			superFields.addAll(fields);
			fields = superFields;
		}

		return fields;
	}

	/**
	 * Find all non-static fields defined in whole hierarchy of given type
	 * @param type the given type
	 * @return the non-static fields and non-static super fields of the given type
	 */
	public static List<Field> retieveAllNonStaticFields(Class<?> type) {
		List<Field> fields = retieveAllFields(type);
		List<Field> filteredFields = new ArrayList<Field>();
		
		for (Field field : fields) {
			if (!Modifier.isStatic(field.getModifiers())) {
				filteredFields.add(field);
			}
		}

		return filteredFields;
	}
}
