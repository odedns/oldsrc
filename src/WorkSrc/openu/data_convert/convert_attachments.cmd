@echo off
call setVars.cmd
java -ms96m -mx400m ConvertAttachments
java -mx64m  ConvertStaticAttachments
