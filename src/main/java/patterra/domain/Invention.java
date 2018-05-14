package patterra.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Invention {
    private Integer id;

    private String applicationNumber;

    private String titleRu;

    protected Date filingOfficeDate;

    protected Set<Document> documentList = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getTitleRu() {
        return titleRu;
    }

    public void setTitleRu(String titleRu) {
        this.titleRu = titleRu;
    }

    public Date getFilingOfficeDate() {
        return filingOfficeDate;
    }

    public void setFilingOfficeDate(Date filingOfficeDate) {
        this.filingOfficeDate = filingOfficeDate;
    }

    public Set<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(Set<Document> documentList) {
        this.documentList = documentList;
    }
}
