<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# candid-intellij-plugin Changelog

## [Unreleased]

## [0.3.2]
### Fixed
- Bump to 222.* version

## [0.3.1]
### Fixed
- Fixed import completion
- Record fields without name now also get primitive type completion

## [0.3.0]
### Added
- 🔧 Line marker on rust files when a matching candid method is found
- 🔧 Resolve candid method to their corresponding rust method
- 🧐 Unused candid method inspection

## [0.2.0]
### Added
- 🪄 Suggest missing import whenever possible
- ️🧐 Marking self import as invalid
- ️🧐 Marking duplicated type name as invalid
- ️🧐 Marking empty and invalid import as invalid
- ✨ Comment code through shortcut
- 📝 Documentation for type reference
- 🔎 Go to symbol for candid type
- 📝 Structure view

### Fixed
- Added missing top level keyword import
- Missing keyword completion for query and oneway
- Stop suggesting top level keyword inside a service

## [0.1.1]
### Added
- Paired bracket and parenthesis
- Quote handler
- Bumped plugin version range to support 2022.1.1

## [0.1.0]
### Added
- Show all types and their path in the completion menu
- Auto insert import when using a type outside the current file
- Added "Candid File" to File > New menu

### Fixed
- Fixed keyword autocompletion in variant and record
- Align comment as well when using the formatter

## [0.0.2]
### Fixed
- Fixed some stuff in CI pipeline
- Remove usage of internal API

## [0.0.1]
### Added
- 💡 Syntax Highlighting
- ⌨️ Auto Completion
- 🔍 Find Usage
- 💄 Code Format
- ⚡ Initial scaffold created from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)