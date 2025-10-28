#!/bin/sh
set -e
WRAPPER_DIR="$(dirname "$0")/.mvn/wrapper"
WRAPPER_JAR="$WRAPPER_DIR/maven-wrapper.jar"
WRAPPER_PROP="$WRAPPER_DIR/maven-wrapper.properties"
download() { url="$1"; dest="$2"; if command -v curl >/dev/null 2>&1; then curl -fsSL "$url" -o "$dest"; elif command -v wget >/dev/null 2>&1; then wget -q "$url" -O "$dest"; else echo "Need curl or wget" >&2; exit 1; fi; }
if [ ! -f "$WRAPPER_JAR" ]; then WRAPPER_URL=$(grep -E "^wrapperUrl=" "$WRAPPER_PROP" | sed -e 's/^wrapperUrl=//'); mkdir -p "$WRAPPER_DIR"; download "$WRAPPER_URL" "$WRAPPER_JAR"; fi
exec "${JAVA_HOME:+$JAVA_HOME/bin/}java" -cp "$WRAPPER_JAR" org.apache.maven.wrapper.MavenWrapperMain "$@"
