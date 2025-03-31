package com.archi.Ecomm_Backend.exceptions;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException{
    private static final long serialVersionUID= 1l;

    String resourceName;
    String field;
    String fieldName;
    Long fieldId;

    public ResourceNotFoundException(String resourceName, String field, String fieldName ){
        super(String.format("%s is not found with %s: %s", resourceName, field, fieldName));
        this.resourceName= resourceName;
        this.field= field;
        this.fieldName= fieldName;
    }

    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("%s is not found with %s: %s"), resourceName, field, fieldId );

    }


}
