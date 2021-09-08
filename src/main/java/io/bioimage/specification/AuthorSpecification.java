package io.bioimage.specification;

public interface AuthorSpecification {

    void setName(String name);

    void setAffiliation(String affiliation);

    void setOrcId(String orcid);

    String getName();

    String getAffiliation();

    String getOrcId();

}
