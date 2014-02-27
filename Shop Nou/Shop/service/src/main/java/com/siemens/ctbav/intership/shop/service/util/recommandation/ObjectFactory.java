
package com.siemens.ctbav.intership.shop.service.util.recommandation;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the recommandservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetRecommandationResponse_QNAME = new QName("http://RecommandService", "getRecommandationResponse");
    private final static QName _WritePreferences_QNAME = new QName("http://RecommandService", "writePreferences");
    private final static QName _WritePreferencesResponse_QNAME = new QName("http://RecommandService", "writePreferencesResponse");
    private final static QName _GetRecommandation_QNAME = new QName("http://RecommandService", "getRecommandation");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: recommandservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetRecommandation }
     * 
     */
    public GetRecommandation createGetRecommandation() {
        return new GetRecommandation();
    }

    /**
     * Create an instance of {@link WritePreferencesResponse }
     * 
     */
    public WritePreferencesResponse createWritePreferencesResponse() {
        return new WritePreferencesResponse();
    }

    /**
     * Create an instance of {@link WritePreferences }
     * 
     */
    public WritePreferences createWritePreferences() {
        return new WritePreferences();
    }

    /**
     * Create an instance of {@link GetRecommandationResponse }
     * 
     */
    public GetRecommandationResponse createGetRecommandationResponse() {
        return new GetRecommandationResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRecommandationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RecommandService", name = "getRecommandationResponse")
    public JAXBElement<GetRecommandationResponse> createGetRecommandationResponse(GetRecommandationResponse value) {
        return new JAXBElement<GetRecommandationResponse>(_GetRecommandationResponse_QNAME, GetRecommandationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WritePreferences }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RecommandService", name = "writePreferences")
    public JAXBElement<WritePreferences> createWritePreferences(WritePreferences value) {
        return new JAXBElement<WritePreferences>(_WritePreferences_QNAME, WritePreferences.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WritePreferencesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RecommandService", name = "writePreferencesResponse")
    public JAXBElement<WritePreferencesResponse> createWritePreferencesResponse(WritePreferencesResponse value) {
        return new JAXBElement<WritePreferencesResponse>(_WritePreferencesResponse_QNAME, WritePreferencesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRecommandation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RecommandService", name = "getRecommandation")
    public JAXBElement<GetRecommandation> createGetRecommandation(GetRecommandation value) {
        return new JAXBElement<GetRecommandation>(_GetRecommandation_QNAME, GetRecommandation.class, null, value);
    }

}
