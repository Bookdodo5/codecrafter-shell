# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Project Overview

This is a Java implementation for the CodeCrafters "Build Your Own Shell" challenge. The project builds a POSIX-compliant shell capable of interpreting shell commands, running external programs, and handling builtin commands like `cd`, `pwd`, `echo`, and more.

## Architecture

### Core Structure
- **Entry Point**: `src/main/java/Main.java` - Contains the main shell implementation
- **Build System**: Maven-based with custom assembly plugin configuration
- **Runtime**: Single JAR deployment with all dependencies bundled

### Key Components
- **Shell Loop**: Implemented in Main.java as a REPL (Read-Eval-Print Loop)
- **Command Parser**: Handles shell command parsing and interpretation
- **Builtin Commands**: Implementation of shell builtins (cd, pwd, echo, etc.)
- **External Programs**: Process execution for external commands

### Build Configuration
- **Java Version**: 23 (source/target), runs on Java 24 buildpack
- **Maven Assembly**: Creates `codecrafters-shell.jar` with all dependencies
- **Main Class**: `Main` (in default package)

## Development Commands

### Building the Project
```bash
# Compile and package (requires Maven)
mvn clean package -Ddir=/tmp/codecrafters-build-shell-java
```

### Running Locally
```bash
# Run the shell program locally
./your_program.sh

# On Windows with Git Bash/WSL, or use:
# bash your_program.sh
```

### Running Compiled JAR Directly
```bash
# After building, run the JAR directly
java -jar /tmp/codecrafters-build-shell-java/codecrafters-shell.jar
```

### CodeCrafters Workflow
```bash
# Submit solution to CodeCrafters
git commit -am "implement feature X"
git push origin master
```

## Development Notes

### Shell Implementation Stages
1. **Stage 1**: Basic shell prompt (`$ `) and input reading
2. **Stage 2+**: Command parsing, builtin commands, external program execution

### Maven Configuration
- Uses `maven-assembly-plugin` to create fat JAR
- Output directory configurable via `-Ddir` parameter
- Final artifact name is `codecrafters-shell.jar` (do not change)

### Platform Considerations
- Shell script (`your_program.sh`) is Unix-style but project runs on Windows
- Build output goes to `/tmp/codecrafters-build-shell-java` (Unix path)
- Use Git Bash, WSL, or similar Unix environment on Windows for shell scripts

### CodeCrafters Integration
- `codecrafters.yml`: Controls debug logging and Java buildpack version
- `.codecrafters/compile.sh`: Remote compilation script (mirrors local build)
- `.codecrafters/run.sh`: Remote execution script
- Debug logs can be enabled in `codecrafters.yml` for troubleshooting

### Code Organization Guidelines
- Keep shell logic modular for different command types
- Separate builtin commands from external program execution
- Handle error cases and provide appropriate shell behavior
- Follow POSIX shell standards where applicable

### Testing Approach
- CodeCrafters provides automated testing via git push
- Local testing can be done by running `./your_program.sh` and manually testing shell commands
- Test both builtin commands and external program execution