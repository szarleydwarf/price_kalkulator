#!/bin/bash
set -e

echo "🔄 Renaming and syncing assets..."

IOS_DIR="ios/pricecalc/Assets.xcassets"
ANDROID_DIR="adr/app/src/main/res/drawable"

mkdir -p "$IOS_DIR" "$ANDROID_DIR"

# enable extended glob handling safely
shopt -s nullglob

for f in assets/*.{png,jpg,jpeg}; do
  filename=$(basename "$f")
  dir=$(dirname "$f")

  # replace hyphens + lowercase
  newfilename=$(echo "$filename" | tr '-' '_' | tr '[:upper:]' '[:lower:]')
  newpath="$dir/$newfilename"

  if [ "$f" != "$newpath" ]; then
    mv "$f" "$newpath"
    f="$newpath"
  fi

  cp "$f" "$IOS_DIR/"
  cp "$f" "$ANDROID_DIR/"
done

echo "✅ Assets renamed and synced"