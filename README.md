# Morpheus Crowdin Plugin

The Morpheus UI Crowdin Plugin enables translators to review and edit Morpheus translations stored within the Morpheus Crowdin project, directly within the Morpheus UI. Translations that are created/edited through the Plugin are saved back to the Crowdin platform and will be built into the next Morpheus UI release. With the Plugin enabled, users can navigate the Morpheus UI as usual and can quickly jump in and out of the Crowdin translation context.

All of the Morpheus UI Strings that can be localized are hosted on the centralized [Crowdin platform](https://hpe-morpheus.crowdin.com/) in the "morpheus" project. Users who wish to provide translations for Morpheus in their chosen language(s) can be granted access to the Crowdin platform where they can provide their translations. All translation activity is saved into the Crowdin platform and the Morpheus Translation Memory.

Where possible, Crowdin will perform AI translation after any 100% Translation Memory matches, but these will require QA approval before being shipped into a release.

## 📑 Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Repository Structure](#repository-structure)
- [Building the Plugin](#building-the-plugin)
- [Installing](#installing)
- [Detailed Usage](#detailed-usage)
- [License](#license)

---

![Crowdin in-context translation example](https://raw.githubusercontent.com/cpdtaylor/morpheus-crowdin-plugin/master/translate-example.gif)

---

## Features

- **In-Context Translation** – Translators can review and edit translations directly within the Morpheus UI, providing the best possible context for accurate translations.
- **Crowdin Integration** – Translations are saved back to the Crowdin platform automatically, ensuring they are included in the next Morpheus UI release.
- **Permission-Based Access** – A "Localisation" feature permission controls which Morpheus users can access the translation context.
- **Custom Locale Injection** – Registers a custom "Translate With Crowdin" locale option that activates the in-context translation mode.
- **Translation Memory & AI** – Leverages Crowdin's Translation Memory for 100% matches and AI-assisted translations (subject to QA approval).
- **QA Approval Workflow** – Only QA-approved translations are shipped into Morpheus UI releases.

---

## Requirements

- Java 17
- Gradle (wrapper included)
- Morpheus appliance version **9.0.0** or later
- Morpheus Plugin API `1.4.0` or later
- Access to the Crowdin "morpheus" project at [hpe-morpheus.crowdin.com](https://hpe-morpheus.crowdin.com/)
- Whitelabel settings enabled on the Morpheus appliance (Administration → Settings → Whitelabel)
- Outbound network connectivity from the Morpheus appliance to `*.crowdin.com`

---

## Repository Structure

- `src/main/groovy/com/morpheusdata/crowdin/`
  - `CrowdinPlugin.groovy` – Plugin entry point; registers the provider and the "Localisation" permission.
  - `CrowdinProvider.groovy` – Core provider implementing `LocalizationProvider` and `AbstractGlobalUIComponentProvider`; handles locale injection, permission checks, CSP configuration, and template rendering.
- `src/main/resources/renderer/hbs/`
  - `crowdin.hbs` – Handlebars template that injects the Crowdin In-Context JavaScript (JIPT) when the custom `zy` locale is active.
- `src/assets/images/` – Plugin logo assets (SVG, light and dark variants).
- `build.gradle` – Gradle build configuration using the Shadow plugin for fat-JAR packaging.
- `gradle.properties` – Version definitions for the plugin, Morpheus Plugin API, Groovy, and dependencies.

---

## Building the Plugin

Run the following command to compile and package the plugin JAR:

```bash
./gradlew clean shadowJar
```

The packaged JAR will be written to `build/libs/morpheus-crowdin-plugin-<version>-all.jar`.

To execute tests:

```bash
./gradlew test
```

---

## Installing

1. Build the plugin JAR (see above) or obtain a pre-built JAR from your Morpheus accounts team.
2. Upload the compiled `morpheus-crowdin-plugin-<version>-all.jar` to your Morpheus appliance via **Administration → Integrations → Plugins**.
3. After upload, the plugin will be active and the "Translate" locale option will be available to users with the appropriate permission.

---

## Detailed Usage

### Configuring Access

The plugin registers a Feature Permission called **"Localisation"**. Assign this permission (set to "Full") to any Morpheus Role whose users should be able to enter the Crowdin translation context. Users without this permission will not see the translate option.

### Entering Translation Mode

1. Click the user avatar/menu in the Morpheus UI.
2. Select the **"Translate With Crowdin"** locale option from the language selector.
3. Authenticate with Crowdin when prompted (or sign in automatically via session cookie).
4. The Morpheus UI will reload in the Crowdin in-context translation mode.

### In-Context Translation

Once in translation mode:

- **Green highlighted strings** – QA-approved translations. Click the pencil icon to edit.
- **Red highlighted strings** – Untranslated strings. Click the pencil icon to provide a translation.
- The **Crowdin Context Settings Window** shows all translatable strings on the current page and allows switching the target language.
- All changes are saved directly to the Crowdin platform.

### Exiting Translation Mode

Revert your user locale settings.

---

## License

This project is licensed under the Apache 2.0 License.
