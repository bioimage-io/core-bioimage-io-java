package io.bioimage.specification;

public class DefaultAuthorSpecification implements AuthorSpecification{

    private String name;
    private String affiliation;
    private String orcId;


    public DefaultAuthorSpecification() {

    }

    public DefaultAuthorSpecification(String name) {
        this.name=name;
    }


    @Override
    public void setName(String name) {
        this.name=name;
    }

    @Override
    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    @Override
    public void setOrcId(String orcid) {
        this.orcId = orcid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAffiliation() {
        return affiliation;
    }

    @Override
    public String getOrcId() {
        return orcId;
    }
}
