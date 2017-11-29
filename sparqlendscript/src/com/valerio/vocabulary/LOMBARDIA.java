package com.valerio.vocabulary;

import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.xsd.impl.RDFLangString;
import org.apache.jena.datatypes.xsd.impl.RDFhtml;
import org.apache.jena.datatypes.xsd.impl.XMLLiteralType;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

import java.io.PrintWriter;

/**
 * Created by valer on 29/11/2017.
 */
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 The standard LOMBARDIA vocabulary.
 */
public class LOMBARDIA {




    /**
     * The namespace of the vocabulary as a string
     */
    public static final String uri = "http://localhost:3030/Lombardia/";

    /** returns the URI for this schema
     @return the URI for this schema
     */
    public static String getURI()
    { return uri; }

    protected static final Resource resource(String local )
    { return ResourceFactory.createResource( uri + local ); }

    protected static final org.apache.jena.rdf.model.Property property(String local )
    { return ResourceFactory.createProperty( uri, local ); }

    public static Property li( int i )
    { return property( "_" + i ); }

    //    public static final Resource
    public static final Property    timestamp          = Init.timestamp();
//    public static final Property    idsensore          = Init.idsensore();


    /** RDF constants are used during Jena initialization.
     * <p>
     * If that initialization is triggered by touching the LOMBARDIA class,
     * then the constants are null.
     * <p>
     * So for these cases, call this helper class: Init.function()
     */
    public static class Init {
        // JENA-1294
        // Version that calculate the constant when called.
        public static Property timestamp()              { return property( "timestamp" ); }
//        public static Property idsensore()              { return property( "idsensore" ); }

    }

    /**
     The same items of vocabulary, but at the Node level, parked inside a
     nested class so that there's a simple way to refer to them.
     */
    @SuppressWarnings("hiding")
    public static final class Nodes
    {
        public static final Node timestamp        = Init.timestamp().asNode();
//        public static final Node idsensore        = Init.idsensore().asNode();

    }
}
