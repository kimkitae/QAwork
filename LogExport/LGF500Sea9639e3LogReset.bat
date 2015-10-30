adb -s LGF500Sea9639e3 logcat -c
adb -s LGF500Sea9639e3 shell dumpsys batterystats --reset
adb -s LGF500Sea9639e3 shell dumpsys batterystats --enable full-wake-history