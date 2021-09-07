package io.bioimage.specification;

public interface BadgeSpecification {

    void setLabel(String label);

    void setIcon(String icon);

    void setUrl(String url);

    String getLabel();

    String getIcon();

    String getUrl();
}
