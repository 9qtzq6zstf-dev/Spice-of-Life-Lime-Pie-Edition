# NeoForge 1.21.1 migration plan

- [x] Preserve the copied 1.20.1 source and resources in an independent Lime Pie repository.
- [x] Add official NeoForge 1.21.1 ModDevGradle build, Java 21 toolchain, and mod metadata.
- [x] Migrate registration, config, events, commands, food API, and client rendering.
- [x] Replace player capabilities with data attachments and item inventory persistence with data components.
- [x] Replace SimpleChannel messages with custom payloads.
- [x] Update recipes, advancement paths, resource pack format, and language strings.
- [ ] Verify optional Origins diet integration against an available NeoForge 1.21.1 build.
- [x] Build and start dedicated server and client smoke tests.
- [x] Run focused GameTests for inventory persistence, player food attachments, missing items, and recipe loading.
- [x] Inspect the final staged change set and create the repository baseline commit.

Keep the `solapplepie` mod ID and resource namespace while showing Lime Pie as the new display name.

Origins verification remains open: the upstream `1.21.x/neo` branch targets Minecraft 1.21 and is dated July 2024; the archived Modrinth project has no published 1.21.1 version.
