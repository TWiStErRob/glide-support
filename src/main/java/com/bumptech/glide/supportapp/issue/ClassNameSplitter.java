package com.bumptech.glide.supportapp.issue;

class ClassNameSplitter {
	private final String fullName;
	private final int classNameDot;
	private final int packageNameDot;
	private final int superNameDot;
	private Class<?> clazz;
	public ClassNameSplitter(Class<?> classObject) {
		this(classObject.getName());
		this.clazz = classObject;
	}
	public ClassNameSplitter(String className) {
		fullName = className;
		classNameDot = fullName.lastIndexOf('.');
		packageNameDot = fullName.lastIndexOf('.', classNameDot-1);
		superNameDot = fullName.lastIndexOf('.', packageNameDot-1);
	}
	boolean isValid() {
		return classNameDot != -1 && packageNameDot != -1 && superNameDot != -1;
	}
	public String getFullPackageName() {
		return fullName.substring(0, classNameDot);
	}
	public String getHostPackageName() {
		return fullName.substring(superNameDot + 1, packageNameDot);
	}
	public String getFullHostPackageName() {
		return fullName.substring(0, packageNameDot);
	}
	public String getPackageName() {
		return fullName.substring(packageNameDot + 1, classNameDot);
	}
	public String getFullName() {
		return fullName;
	}
	public String getClassName() {
		return fullName.substring(classNameDot + 1, fullName.length());
	}
	public Class<?> getClassObject() {
		try {
			if (clazz == null) {
				clazz = Class.forName(fullName);
			}
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
		return clazz;
	}
}
