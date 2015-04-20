@echo off

rem using ant to backup the dy database

echo "Start..."

d:
cd %cd%

ant dmpExport


echo "End."