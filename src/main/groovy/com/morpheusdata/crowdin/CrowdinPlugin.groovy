/**
@author Chris Taylor
**/

package com.morpheusdata.crowdin

import com.morpheusdata.core.Plugin
import com.morpheusdata.model.Permission

/**
 * Plugin to enable Context translations in Morpheus
 */
class CrowdinPlugin extends Plugin {

	@Override
	String getCode() {
		return 'crowdin-translations-plugin'
	}

	@Override
	void initialize() {
		CrowdinProvider crowdinProvider = new CrowdinProvider(this, morpheus)
		this.pluginProviders.put(crowdinProvider.providerCode, crowdinProvider)
		this.setName("Crowdin Plugin")
		this.setPermissions([Permission.build('Localisation','localisation', [Permission.AccessType.none, Permission.AccessType.full])])
	}

	@Override
	void onDestroy() {
	}
}
