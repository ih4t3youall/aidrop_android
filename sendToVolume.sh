#!/bin/bash

echo "Removing qpp-debug.apk"
rm /Volumes/SANDISK/app-debug.apk
echo "Coping files"
cp /Users/martinlequerica/Documents/workspace/AndroidDrop/app/debug/app-debug.apk /Volumes/SANDISK/
echo "Removing ._app-debug.apk file"
rm /Volumes/SANDISK/._app-debug.apk
echo "SANDISK search"
echo $(diskutil list | grep -i SANDISK)
echo "Unmounting disk 5"
diskUtil unmountDisk /dev/disk5
