/*
 * An XML document type.
 * Localname: __get
 * Namespace: urn:SY_SoapServer
 * Java type: sy_soapserver.GetDocument2
 *
 * Automatically generated - do not modify.
 */
package sy_soapserver;


/**
 * A document containing one __get(@urn:SY_SoapServer) element.
 *
 * This is a complex type.
 */
public interface GetDocument2 extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetDocument2.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s4262BC6C064011807FED7385999847C2").resolveHandle("get2d1bdoctype");
    
    /**
     * Gets the "__get" element
     */
    sy_soapserver.GetDocument2.Get getGet();
    
    /**
     * Sets the "__get" element
     */
    void setGet(sy_soapserver.GetDocument2.Get get);
    
    /**
     * Appends and returns a new empty "__get" element
     */
    sy_soapserver.GetDocument2.Get addNewGet();
    
    /**
     * An XML __get(@urn:SY_SoapServer).
     *
     * This is a complex type.
     */
    public interface Get extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Get.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s4262BC6C064011807FED7385999847C2").resolveHandle("getc47delemtype");
        
        /**
         * Gets the "name" element
         */
        java.lang.String getName();
        
        /**
         * Gets (as xml) the "name" element
         */
        org.apache.xmlbeans.XmlString xgetName();
        
        /**
         * Tests for nil "name" element
         */
        boolean isNilName();
        
        /**
         * Sets the "name" element
         */
        void setName(java.lang.String name);
        
        /**
         * Sets (as xml) the "name" element
         */
        void xsetName(org.apache.xmlbeans.XmlString name);
        
        /**
         * Nils the "name" element
         */
        void setNilName();
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static sy_soapserver.GetDocument2.Get newInstance() {
              return (sy_soapserver.GetDocument2.Get) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static sy_soapserver.GetDocument2.Get newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (sy_soapserver.GetDocument2.Get) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static sy_soapserver.GetDocument2 newInstance() {
          return (sy_soapserver.GetDocument2) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static sy_soapserver.GetDocument2 newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (sy_soapserver.GetDocument2) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static sy_soapserver.GetDocument2 parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (sy_soapserver.GetDocument2) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static sy_soapserver.GetDocument2 parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (sy_soapserver.GetDocument2) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static sy_soapserver.GetDocument2 parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (sy_soapserver.GetDocument2) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static sy_soapserver.GetDocument2 parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (sy_soapserver.GetDocument2) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static sy_soapserver.GetDocument2 parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (sy_soapserver.GetDocument2) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static sy_soapserver.GetDocument2 parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (sy_soapserver.GetDocument2) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static sy_soapserver.GetDocument2 parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (sy_soapserver.GetDocument2) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static sy_soapserver.GetDocument2 parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (sy_soapserver.GetDocument2) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static sy_soapserver.GetDocument2 parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (sy_soapserver.GetDocument2) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static sy_soapserver.GetDocument2 parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (sy_soapserver.GetDocument2) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static sy_soapserver.GetDocument2 parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (sy_soapserver.GetDocument2) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static sy_soapserver.GetDocument2 parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (sy_soapserver.GetDocument2) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static sy_soapserver.GetDocument2 parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (sy_soapserver.GetDocument2) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static sy_soapserver.GetDocument2 parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (sy_soapserver.GetDocument2) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static sy_soapserver.GetDocument2 parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (sy_soapserver.GetDocument2) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static sy_soapserver.GetDocument2 parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (sy_soapserver.GetDocument2) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
