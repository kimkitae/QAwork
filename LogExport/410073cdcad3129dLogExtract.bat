set date2=%date:-=%
set time2=%time: =0%
set time3=%time2:~0,2%%time2:~3,2%%time:~6,2%
set filename=410073cdcad3129dlogcat_log_%date2%%time3%.txt
set filename1=410073cdcad3129dbatterystats_%date2%%time3%.txt
adb -s 410073cdcad3129d shell dumpsys batterystats > 410073cdcad3129d/%filename1%
adb -s 410073cdcad3129d logcat -v time > 410073cdcad3129d/%filename%