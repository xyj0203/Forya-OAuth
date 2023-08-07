/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wojucai.controller;

import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.*;

/**
 * @author Daniel Garnier-Moiroux
 */
@Controller
public class AuthorizationConsentController {
	private final RegisteredClientRepository registeredClientRepository;
	private final OAuth2AuthorizationConsentService authorizationConsentService;

	public AuthorizationConsentController(RegisteredClientRepository registeredClientRepository,
                                          OAuth2AuthorizationConsentService authorizationConsentService) {
		this.registeredClientRepository = registeredClientRepository;
		this.authorizationConsentService = authorizationConsentService;
	}

	/**
	 * 授权访问
	 * @param principal 认证权限
	 * @param model 视图对象
	 * @param clientId 客户端索引
	 * @param scope 作用域
	 * @param state 状态码
	 * @return
	 */
	@GetMapping(value = "/oauth2/consent")
	public String consent(Principal principal, Model model,
			@RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
			@RequestParam(OAuth2ParameterNames.SCOPE) String scope,
			@RequestParam(OAuth2ParameterNames.STATE) String state) {

		// Remove scopes that were already approved
		Set<String> scopesToApprove = new HashSet<>();
		Set<String> previouslyApprovedScopes = new HashSet<>();
		// 根据ClientID获取对应的客户端对象
		RegisteredClient registeredClient = this.registeredClientRepository.findByClientId(clientId);
		OAuth2AuthorizationConsent currentAuthorizationConsent =
				this.authorizationConsentService.findById(registeredClient.getId(), principal.getName());
		Set<String> authorizedScopes;
		// 当前的不为空
		if (currentAuthorizationConsent != null) {
			authorizedScopes = currentAuthorizationConsent.getScopes();
		} else {
			// 当前的为空
			authorizedScopes = Collections.emptySet();
		}
		// 通过定界符将List转为数组
		for (String requestedScope : StringUtils.delimitedListToStringArray(scope, " ")) {
			// 如果已经认证的作用域包含
			if (authorizedScopes.contains(requestedScope)) {
				// 添加到先前的
				previouslyApprovedScopes.add(requestedScope);
			} else {
				// 添加到现在的
				scopesToApprove.add(requestedScope);
			}
		}

		// 客户端ID
		model.addAttribute("clientId", clientId);
		// 状态码
		model.addAttribute("state", state);
		// 现在同意的作用域
		model.addAttribute("scopes", withDescription(scopesToApprove));
		// 先前支持的作用域
		model.addAttribute("previouslyApprovedScopes", withDescription(previouslyApprovedScopes));
		model.addAttribute("principalName", principal.getName());

		return "consent";
	}

	/**
	 * 授权访问
	 * @param principal 认证权限
	 * @param clientId 客户端索引
	 * @param scope 作用域
	 * @param state 状态码
	 * @return
	 */
	@GetMapping(value = "/oauth2/consent")
	public String test(Principal principal,
						  @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
						  @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
						  @RequestParam(OAuth2ParameterNames.STATE) String state) {

		Set<String> scopesToApprove = new HashSet<>();
		Set<String> previouslyApprovedScopes = new HashSet<>();
		RegisteredClient registeredClient = this.registeredClientRepository.findByClientId(clientId);
		return "consent";
	}

	private static Set<ScopeWithDescription> withDescription(Set<String> scopes) {
		Set<ScopeWithDescription> scopeWithDescriptions = new HashSet<>();
		for (String scope : scopes) {
			scopeWithDescriptions.add(new ScopeWithDescription(scope));

		}
		return scopeWithDescriptions;
	}

	/**
	 * 修饰作用域
	 */
	public static class ScopeWithDescription {
		private static final String DEFAULT_DESCRIPTION = "UNKNOWN SCOPE - We cannot provide information about this permission, use caution when granting this.";
		private static final Map<String, String> scopeDescriptions = new HashMap<>();
		static {
			scopeDescriptions.put(
					"message.read",
					"This application will be able to read your message."
			);
			scopeDescriptions.put(
					"message.write",
					"This application will be able to add new messages. It will also be able to edit and delete existing messages."
			);
			scopeDescriptions.put(
					"other.scope",
					"This is another scope example of a scope description."
			);
		}

		public final String scope;
		public final String description;

		ScopeWithDescription(String scope) {
			this.scope = scope;
			this.description = scopeDescriptions.getOrDefault(scope, DEFAULT_DESCRIPTION);
		}
	}


}
