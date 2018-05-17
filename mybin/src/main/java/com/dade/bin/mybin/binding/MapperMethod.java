package com.dade.bin.mybin.binding;


// TODO 增加返回值是Collection 还是 Entity 的判断
public class MapperMethod {

//    private final boolean returnsMany;
//    private final boolean returnsMap;
//    private final boolean returnsVoid;
//    private final boolean returnsCursor;
//    private final boolean returnsOptional;
//    private final Class<?> returnType;
//    private final String mapKey;
//    private final Integer resultHandlerIndex;
//    private final Integer rowBoundsIndex;
//    private final ParamNameResolver paramNameResolver;
//
//    public MapperMethod(BinConfig configuration, Class<?> mapperInterface, Method method) {
//        Type resolvedReturnType = TypeParameterResolver.resolveReturnType(method, mapperInterface);
//        if (resolvedReturnType instanceof Class<?>) {
//            this.returnType = (Class<?>) resolvedReturnType;
//        } else if (resolvedReturnType instanceof ParameterizedType) {
//            this.returnType = (Class<?>) ((ParameterizedType) resolvedReturnType).getRawType();
//        } else {
//            this.returnType = method.getResultType();
//        }
//        this.returnsVoid = void.class.equals(this.returnType);
//        this.returnsMany = configuration.getObjectFactory().isCollection(this.returnType) || this.returnType.isArray();
//        this.returnsCursor = Cursor.class.equals(this.returnType);
//        this.returnsOptional = Jdk.optionalExists && Optional.class.equals(this.returnType);
//        this.mapKey = getMapKey(method);
//        this.returnsMap = this.mapKey != null;
//        this.rowBoundsIndex = getUniqueParamIndex(method, RowBounds.class);
//        this.resultHandlerIndex = getUniqueParamIndex(method, ResultHandler.class);
//        this.paramNameResolver = new ParamNameResolver(configuration, method);
//    }
//
//    public Object convertArgsToSqlCommandParam(Object[] args) {
//        return paramNameResolver.getNamedParams(args);
//    }
//
//    public boolean hasRowBounds() {
//        return rowBoundsIndex != null;
//    }
//
//    public RowBounds extractRowBounds(Object[] args) {
//        return hasRowBounds() ? (RowBounds) args[rowBoundsIndex] : null;
//    }
//
//    public boolean hasResultHandler() {
//        return resultHandlerIndex != null;
//    }
//
//    public ResultHandler extractResultHandler(Object[] args) {
//        return hasResultHandler() ? (ResultHandler) args[resultHandlerIndex] : null;
//    }
//
//    public String getMapKey() {
//        return mapKey;
//    }
//
//    public Class<?> getResultType() {
//        return returnType;
//    }
//
//    public boolean returnsMany() {
//        return returnsMany;
//    }
//
//    public boolean returnsMap() {
//        return returnsMap;
//    }
//
//    public boolean returnsVoid() {
//        return returnsVoid;
//    }
//
//    public boolean returnsCursor() {
//        return returnsCursor;
//    }
//
//    /**
//     * return whether return type is {@code java.util.Optional}
//     * @return return {@code true}, if return type is {@code java.util.Optional}
//     * @since 3.5.0
//     */
//    public boolean returnsOptional() {
//        return returnsOptional;
//    }
//
//    private Integer getUniqueParamIndex(Method method, Class<?> paramType) {
//        Integer index = null;
//        final Class<?>[] argTypes = method.getParameterTypes();
//        for (int i = 0; i < argTypes.length; i++) {
//            if (paramType.isAssignableFrom(argTypes[i])) {
//                if (index == null) {
//                    index = i;
//                } else {
//                    throw new BindingException(method.getName() + " cannot have multiple " + paramType.getSimpleName() + " parameters");
//                }
//            }
//        }
//        return index;
//    }
//
//    private String getMapKey(Method method) {
//        String mapKey = null;
//        if (Map.class.isAssignableFrom(method.getResultType())) {
//            final MapKey mapKeyAnnotation = method.getAnnotation(MapKey.class);
//            if (mapKeyAnnotation != null) {
//                mapKey = mapKeyAnnotation.value();
//            }
//        }
//        return mapKey;
//    }

}
