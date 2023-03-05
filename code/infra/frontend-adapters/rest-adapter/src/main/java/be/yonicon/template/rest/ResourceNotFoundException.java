package be.yonicon.template.rest;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceType, String id) {
        super(String.format("resource type %s is not found with id %s.", resourceType, id));
    }
}
