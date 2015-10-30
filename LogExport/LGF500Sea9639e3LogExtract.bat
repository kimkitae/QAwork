set date2=%date:-=%
set time2=%time: =0%
set time3=%time2:~0,2%%time2:~3,2%%time:~6,2%
set filename=LGF500Sea9639e3logcat_log_%date2%%time3%.txt
set filename1=LGF500Sea9639e3batterystats_%date2%%time3%.txt
adb -s LGF500Sea9639e3 shell dumpsys batterystats > LGF500Sea9639e3/%filename1%
adb -s LGF500Sea9639e3 logcat -v time > LGF500Sea9639e3/%filename%