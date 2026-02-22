# Changelog

All notable changes to this project will be documented in this file.

## v0.1.2.9.2 — 2026-02-23

### Added
- Pure Daisy automation: converts water into Snow Blocks every 10 seconds (200 ticks).
- Tier multipliers for generated Snow Blocks: base = 2, upgraded = 4, advanced = 8, ultimate = 16.
- consumption is 1000 mB of water per Snow Block.

### Changed
- Daisy now fills 8 inventory slots up to the tier multiplier every 10s, bounded by available water and slot limits.
- Progress towards the next generation cycle (waterProgress) is persisted to NBT and dispatched to clients.

### Fixed
- Various syntax and comment cleanups; compile issues resolved.


## v0.1.2.9 — 2025-12-23

### Added
- Conditional recipe loading for MythicBotany-dependent machines
  - Four Mana Infuser recipes (`base`, `upgraded`, `advanced`, `ultimate`) now include a top-level Forge condition (`"conditions": [{ "type": "forge:mod_loaded", "modid": "mythicbotany" }]`) so they are only loaded when the `mythicbotany` mod is present.
- Checking if mana storage is full before inserting mana
  - Mana insertion logic now verifies if the target mana storage can accept more mana before performing the insertion, preventing overflows and voids.

### Changed
- JEI integration hardened for optional MythicBotany compatibility
  - Reflection lookup for MythicBotany JEI/recipe types is now cached and guarded by `ModList` checks to avoid ClassNotFound/Linkage errors.

### Fixed
- Prevent recipe/resource parsing errors when MythicBotany is not installed
  - Conditional recipe loading prevents JSON parser errors for missing MythicBotany items (e.g. `mythicbotany:mana_infuser`).
---
*Generated on 2025-12-23.*