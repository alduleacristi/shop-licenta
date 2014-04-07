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

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;

import org.apache.mahout.cf.taste.common.TasteException;

import com.siemens.ctbav.intership.recommandation.service.RecommandationEngine;

@WebService(serviceName = "RecommandService", portName = "Recommand", name = "Recommand", endpointInterface = "com.siemens.ctbav.intership.recommandation.RecommandService", targetNamespace = "http://RecommandService")
public class RecommandServiceImpl implements RecommandService {
	@EJB
	private RecommandationEngine recommandationEngine;

	public void writePreferences(Long idUser, List<Long> preferences) {
		recommandationEngine.writePreferences(idUser, preferences);

	}

	public List<Long> getRecommandation(Long idUser) {
		try {
			List<Long> list = recommandationEngine
					.getRecomandation(idUser, 10L);

			return list;
		} catch (TasteException e) {
			e.printStackTrace();

			return new ArrayList<Long>();
		}

	}

}
