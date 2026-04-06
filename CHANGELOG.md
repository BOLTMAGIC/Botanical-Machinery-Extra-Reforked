# Changelog

All notable changes to this project will be documented in this file.

## v0.1.2.9.5 — 2026-04-06
### Fixed
- Fixed machines not getting mana and don't auto output items to the ME system.

## v0.1.2.9.4 — 2026-03-12

### Changed
- Performance: Wide-ranging optimizations to recipe/pattern handling to reduce CPU usage in hot paths
  - Added: `RecipeValidityCache` — a lightweight, conservative cache for frequent calls to `RecipeHelper.isItemValidInput(...)`. ItemStacks with NBT are not cached; cache is invalidated on datapack/recipe reload.
  - Ingredient fast-paths: simple Ingredients are now preloaded internally as `Item[]` arrays and matched by item equality; only complex (NBT/partial-NBT) Ingredients fall back to `Ingredient.test(...)`.
  - Hot-path refactors: iterators/streams in pattern/recipe matching paths replaced with index-based loops, and unnecessary temporary allocations reduced.
  - Cache invalidation: all relevant caches are cleared on datapack/recipe reload (wired into `EventListener`).

### Fixed
- Fixes during refactor/optimization: several compiler errors caused by inconsistent intermediate edits were corrected; project builds successfully (BUILD SUCCESSFUL).

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