/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.siemens.ctbav.intership.recommandation;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(targetNamespace = "http://RecommandService")
public interface RecommandService {

    /**
     * Write preferences of an user
     * @param idUser The id of the user
     * @param preferences The lsit with the preferences of the user
     */
    @WebMethod
    public void writePreferences(Long idUser,List<Long> preferences);

    /**
     * Get recommandetion for an user
     * 
     * @param idUser The id of the user
     * @return the list of recommandetion
     */
    @WebMethod
    public List<Long> getRecommandation(Long idUser);
}
