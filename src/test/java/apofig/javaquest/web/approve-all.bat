@echo off
setlocal enableDelayedExpansion
for %%F in (*.received.txt) do (
  set "name=%%F"
  move /Y "%%F" "!name:~0,-13!.approved.txt"
)