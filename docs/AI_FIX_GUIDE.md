# AI Fix Guide

This document records why the project broke during previous AI-assisted edits, what was actually wrong, and how future AI agents should approach changes in this repository.

## Purpose

Use this file as the first-read guide before making non-trivial changes to the Android project.

The goal is to prevent the following repeated failure pattern:

1. AI generates Kotlin code without checking the existing project structure.
2. Build files, layouts, resources, DAO/repository contracts, and runtime threading become inconsistent.
3. Fixes are applied in the wrong order.
4. The project ends up with dozens of cascading compile and runtime errors.

## What Went Wrong Previously

The earlier AI changes failed for engineering reasons, not because Android itself was unusually hard.

### 1. Build baseline was not stabilized first

The project had multiple environment-level issues:

- repository configuration conflict between `settings.gradle` and `build.gradle`
- missing AndroidX configuration
- incompatible Gradle / AGP / SDK / JDK combinations
- Room / kapt compatibility issues

Instead of stabilizing the build chain first, code generation continued on top of an unstable environment.

### 2. Code was generated without matching resources

Several Kotlin files referenced resources that did not exist:

- missing layouts
- missing binding classes
- missing themes
- missing strings
- missing icons
- missing XML backup/data extraction files

This strongly suggests the previous AI wrote activity/fragment/business code without verifying whether the corresponding Android resources existed.

### 3. Data-layer contracts were inconsistent

The project ended up with mismatches between:

- Room DAO definitions
- repository wrappers
- ViewModel call sites
- coroutine/threading expectations

At one point, DAO methods were `suspend`, but kapt/Room could not process them correctly in the local toolchain. After changing them to synchronous methods, some repository calls still ran on the main thread and caused runtime crashes.

### 4. Some files were overwritten in a low-confidence way

Examples included:

- duplicate or broken `strings.xml` entries
- corrupted string literals in `AppDatabase.kt`
- code referencing nonexistent classes such as `RecipeConverters`

This is typical of large, unchecked, overwrite-style AI edits.

## Root Cause Summary

The main problem was not "a single bad line."

The real root cause was:

AI treated the task as "generate code" instead of "repair an Android project with strict cross-file constraints."

Android projects require consistency across:

- Gradle files
- SDK/toolchain versions
- manifest
- layouts
- drawables
- values resources
- databinding/viewbinding
- Room entities/DAO/repository/viewmodel flow
- main-thread vs IO-thread database access

If these are not updated together and verified incrementally, the project breaks quickly.

## Correct Way For AI To Work In This Repository

Future AI should follow this order strictly.

### Step 1. Read before editing

Before making changes, inspect at least:

- `build.gradle`
- `app/build.gradle`
- `settings.gradle`
- `gradle.properties`
- `app/src/main/AndroidManifest.xml`
- `app/src/main/res/layout`
- `app/src/main/res/values`
- relevant DAO / repository / ViewModel files

Do not assume any generated code has matching XML/resources.

### Step 2. Fix the build baseline first

Always stabilize the toolchain before touching business logic:

1. repository definitions
2. AndroidX flags
3. AGP / Gradle / compileSdk compatibility
4. Kotlin / Room / Navigation compatibility

If the project cannot sync/build cleanly at the configuration level, do not generate more feature code.

### Step 3. Resolve only the first active error chain

When building:

- fix the earliest blocking error
- rebuild
- use the next error as the source of truth

Do not "batch invent" unrelated fixes without verification.

### Step 4. Treat Android resources as first-class code

Whenever AI adds or edits:

- an `Activity`
- a `Fragment`
- a notification helper
- databinding/viewbinding code
- a toolbar/menu/detail screen

it must verify matching:

- layout XML
- string resources
- colors
- drawables/icons
- theme/style references
- manifest entries

No Kotlin file should be considered complete until its Android resources are present and named consistently.

### Step 5. Keep threading consistent

For this project, if DAO methods are synchronous, repository methods that touch Room must switch to:

`withContext(Dispatchers.IO)`

If DAO methods are `suspend`, the AI must confirm the local Room/kapt toolchain actually supports that configuration in this project.

Never call Room synchronously from a ViewModel on the main thread.

### Step 6. Avoid large blind rewrites

High-risk files:

- `strings.xml`
- `AndroidManifest.xml`
- `AppDatabase.kt`
- large activity/fragment files

If editing these files:

- prefer minimal changes
- preserve existing naming and structure
- rebuild immediately after changes

### Step 7. Explain why each change is being made

A useful AI answer should always say:

- what error chain is being fixed
- why the selected fix matches that error
- whether the fix was actually validated by a build or run
- what remains risky or unverified

## Specific Lessons From This Repair

### Build/config lessons

- `settings.gradle` repository policy and project-level repositories must not conflict.
- AndroidX must be explicitly enabled when the build/plugins require it.
- AGP, Gradle, compileSdk, and local JDK/JBR must be treated as a compatibility set, not independent values.

### Resource lessons

- Missing resource references in manifest/theme/icon/layout files cause large cascades.
- Databinding/viewbinding class names are determined by actual layout filenames.
- Code should not reference IDs that are not present in XML.

### Room/data lessons

- Missing converter references break annotation processing immediately.
- Corrupted seed/sample code inside database classes can break compilation even if business logic is fine.
- Repository is the safest place to enforce IO-thread dispatch for synchronous DAO access.

## Recommended AI Prompt Style For This Repository

When asking AI to fix this project, use instructions like:

"First inspect the Gradle files, manifest, resources, DAO/repository/viewmodel chain, and only then make minimal changes. Fix only the current blocking build/runtime error, rebuild mentally against the current logs, and do not add code that references missing layouts/resources."

Good additional constraints:

- "Do not rewrite large files unless necessary."
- "Check whether referenced XML/resources already exist before coding."
- "Keep Room access off the main thread."
- "Explain the exact reason for each fix."

## Current Practical Rule

If a future AI starts making changes in this project, it should read this document first and treat the repository as an Android integration problem, not just a Kotlin coding task.

