package com.morpheusdata.crowdin

import com.morpheusdata.core.AbstractGlobalUIComponentProvider
import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.Plugin
import com.morpheusdata.model.Account
import com.morpheusdata.model.Instance
import com.morpheusdata.model.TaskConfig
import com.morpheusdata.model.ContentSecurityPolicy
import com.morpheusdata.model.User
import com.morpheusdata.views.HTMLResponse
import com.morpheusdata.views.ViewModel
import com.morpheusdata.model.UIScope
import com.morpheusdata.model.Permission

/**
 * CrowdinProvider Class
 */
class CrowdinProvider extends AbstractGlobalUIComponentProvider {
	Plugin plugin
	MorpheusContext morpheus

	CrowdinProvider(Plugin plugin, MorpheusContext context) {
		this.plugin = plugin
		this.morpheus = context
	}

	@Override
	MorpheusContext getMorpheus() {
		morpheusContext
	}

	@Override
	Plugin getPlugin() {
		plugin
	}

	@Override
	String getCode() {
		'crowdin-1'
	}
	
	String getProviderCode() {
		'crowdin-1'
	}

	@Override
	String getName() {
		'Crowdin 1'
	}
	
	String getProviderName() {
		'Crowdin 1'
	}

	@Override
	HTMLResponse renderTemplate(User user, Account account) {
		ViewModel<String> model = new ViewModel<String>()
		def nonse = morpheus.getWebRequest().getNonceToken()
		model.object = nonse.toString()
		getRenderer().renderTemplate("hbs/crowdin", model)
	}

	@Override
	Boolean show(User user, Account account) {
		def show = true
		plugin.permissions.each { Permission permission ->
			if(user.permissions[permission.code] != permission.availableAccessTypes.last().toString()){
				show = false
			}
		}
		return show
	}

	@Override
	ContentSecurityPolicy getContentSecurityPolicy() {
		def csp = new ContentSecurityPolicy()
		
		csp.scriptSrc = '*.crowdin.com crowdin.com'
		csp.frameSrc = '*.crowdin.com crowdin.com'
		csp.imgSrc = '*.crowdin.com crowdin.com *.gravatar.com gravatar.com \'self\' i2.wp.com data:'
		csp.styleSrc = 'https: *.crowdin.com crowdin.com'
		csp.connectSrc = '*.crowdin.com crowdin.com'
		csp
	}
}
