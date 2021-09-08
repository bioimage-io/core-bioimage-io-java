package io.bioimage.specification;

public class DefaultParentSpecification implements ParentSpecification {
    private String hash;
    private String uri;

    @Override
    public String getHash() {
        return hash;
    }

    @Override
    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public void setUri(String uri) {
        this.uri = uri;
    }
}
