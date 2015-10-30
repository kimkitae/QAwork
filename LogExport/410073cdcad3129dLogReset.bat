adb -s 410073cdcad3129d logcat -c
adb -s 410073cdcad3129d shell dumpsys batterystats --reset
adb -s 410073cdcad3129d shell dumpsys batterystats --enable full-wake-history