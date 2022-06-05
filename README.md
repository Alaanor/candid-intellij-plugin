<p align="center">
  <img src="src/main/resources/META-INF/pluginIcon.svg" width="128" />
</p>
<p align="center">
  <a href="https://github.com/Alaanor/candid-intellij-plugin/actions/workflows/build.yml"><img src="https://github.com/Alaanor/candid-intellij-plugin/workflows/Build/badge.svg" alt="Build"></a>
  <a href="https://plugins.jetbrains.com/plugin/19148"><img src="https://img.shields.io/jetbrains/plugin/v/19148.svg" alt="Version"></a>
  <a href="https://plugins.jetbrains.com/plugin/19148"><img src="https://img.shields.io/jetbrains/plugin/d/19148.svg" alt="Downloads"></a>
</p>

# <img src="src/main/resources/icon/fileType.svg" width="24"/> IntelliJ Candid Language Plugin

<!-- Plugin description -->
A [Candid](https://github.com/dfinity/candid) language plugin that provide a complete support to efficiently edit `.did` files. [Candid](https://github.com/dfinity/candid) is an interface description language (IDL) for interacting with canisters (also known as services or actors) running on the [Internet Computer](https://internetcomputer.org/).

Features supported so far:
- 💡 Syntax Highlighting
- ⌨️ Auto Completion
- 🔍 Find Usage
- 💄 Code Format
- 🦀 Rust integration
<!-- Plugin description end -->

## 🦀  Rust integration

For the sake of correctness, the plugin will only enable rust integration for a given candid file if the followings are found in `dfx.json`:

```json
{
  "canisters": {
    "foobar-canister": {
      "type": "rust",
      "candid": "correct/path/to/candid-file.did",
      "package": "rust-package-name"
    }
  }
}
```

All three `type`, `candid` and `package` fields are required to enable rust integration. `dfx.json` is expected to be found at the root of the project.
The type `custom` will not be supported because of the lack of explicit information that the plugin require to correctly resolve items.

## Installation

- Using IDE built-in plug system: <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "<a href="https://plugins.jetbrains.com/plugin/19148">Candid</a>"</kbd> >
  <kbd>Install Plugin</kbd>
- Manually: Download the [latest release](https://github.com/Alaanor/candid-intellij-plugin/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>
