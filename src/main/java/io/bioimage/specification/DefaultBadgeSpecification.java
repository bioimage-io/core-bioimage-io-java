package io.bioimage.specification;

public class DefaultBadgeSpecification implements BadgeSpecification{

    String label;
    String icon;
    String url;

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getIcon() {
        return icon;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
