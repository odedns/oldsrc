��        set  rceRCE            promptPROMPT   defDEF   noechoNOECHO   upperUPPER   lowerLOWER   trimTRIM       timo=TIMO=   readkeyREADKEY             repREP       chrCHR   elemELEM    midMID                                                                                              locLOC   lenLEN sumSUM  ++  qeQEtlTLelELtgTGegEG    chgupCHGUP   chgloCHGLO   dosvDOSV dosmDOSM cwdCWD cfdCFD timeTIME dateDATE1 driveDRIVE PATH= COMSPEC= PROMPT= SETENV= CONFIG= TEMP= TMP= winbootdir=Set Environment Variable Program V2.5 (c) 18-Aug-1995 by John Wolchak.
Usage is:  SETENV <variable> <value>  or  SETENV %rce <variable> <value>
	<variable> by it's self to erase variable.
	<value> can be: '%rep<del><txt1><del><txt2><del>' text replace.
	  '%chgup' or 'chglo' upper/lower case environment variable data.
	  '%prompt %noecho %upper %def<del><text><del> <message>'.
	     '%noecho' for no echo, '%upper' '%lower' upper/lower case only.
	     '%def<del><text><del>' supply a default, '%trim' trim spaces.
	     '%timo=hh:mm:ss' prompt time out, time length.
	<value> can include keys such as:   ie  setenv drivedir %drive:%cwd
	  %dosv / %dosm for DOS major / minor version.
	  %cwd or %cfd for current directory,   %drive for drive letter.
	  %time / %daten for current time / date format 'n'.
	  %+n / %-n for default drive plus / minus 'n'.
	  %loc/<n>/<var>/<str>/  %len/<var>/  %sum/<var>/<n>/
	  %mid/<var>/<n1>/<n2>/  %elem/<n>/<char>/<var>/  %chr/<n>/
Note:     Use %% to represent a single % in BAT files.
       SETENV return codes:		no command, help issued 	00h
root environment set		01h	environment variable erased	02h
environment variable created	03h	don't know about MS-DOS V1.x	04h
environment variable updated	05h	unmatched delimiter		06h
not a valid argument		07h	environment variable not found	08h
offset drive will be invalid	09h	environment space is full	10h$Error - can't find SETENV at top of environment space.$���� �< u��	�!� �j�0�!�
�&��u��R�< t�<	t�<%t<t�N�+�V�����t^V�����t^��^FFF� �< t�<	t�N�6�\�< t<	t<=t<t<z<ar, �����=��D��Ƌ+ã �< t�<	t�N�<%t�<%t�<t�����X�62FWV���&�$�t^V���,�$�t�w�^VN�Ƌ"���<t�u�д�!��^V���߃>� u�� �,�!��6���<r��<�����<r��<��.���r���6����.���� ���3� �!���3���!��!< uI�,�!�>� t'�� u�� u�� uހ� t٠��Ȣ��,�!�� t���:.�r�:�r�:6�r�����3����!�>� t��g���!�>Zu���>hu���>�u�>L u�д�!��<t<u"��;�tÃ>L u���!� ��!���!O맃>L u�д�!< u�>tt��t  �<u����!�
��!�>vuOO�< t�G��^V�<u���B�<��^_�>�t����������6x< t�<	t�<%tN�Ƌ2+ã"�/��6x�@�>�t�6x�F�>�t�	��L 뷋6x�P�N�t�6x�U�N�t�	��Z 뎋6x�^�\�t�6x�c�\�t�
��h �d��6x�l�j�t�6x�p�j�t���t �v �4��6x�����t�6x�����t�
��� �
��6x�|�z�t�6x���z�t�K��<:t"<.t<-t<0r/<9+,0�����
��Ǣ��٠������������ ������ N���6x�6�4�t�6x�9�4�t�6x����пB�6<�<t:�t���T�� �$�>�d�DN.��܋Ƌ<+�H�<�=�^V�������t^V�������t�Q�����п��  �<t:�t�A��N.��������  �<t:�t�A��N.������ ^_F�	^V���g�e�t^V���l�e�t���q� ^_F�i	^V���u�s�t^V���z�s�t���� ^_F�<	^V��ଋN;�tN�P;�tF�R;�tK�T;�tC�V;�t6�X;�t.�Z;�t8�\;�t0�^;�t#�`;�t^_N�(��b����b��c����b��d����< t#<	t<'t<"t�� ���� �>tP���,��< t�<	t�<'t�<"t�<t+N�� �>t)N�������< t�<	t�<'t<"t<u".����E� ��� �>t����N� �>tܿ����������������� t�� t'N�IO�J:�t�-�>c�t2�+��� t�>d�t#���>c�t���>b�t���>d�t�F���T^VN�����^_N����  �Ь< t�<	t�N�:�t<t�A��.�ù  �����< t<	t<t��
�A��� t����>� t.�ÊD<+t<-u:���T��0r ��9��0��!a��+t*�<a}�	�%�b��<z�FFF�O�WV��G�����t^V��G�����t^_� ���^_��\�V�T�G� �!�< t���^��WV��G�����t^V��G�����t^_�)���^_��V�T�G� �!�< t�\��< t����\�^��WV��G�����t^V��G�����t^_����^_��0�!0���WV��G�����t^V��G�����t^_����^_��0�!��<
r���0��H�WV��G�����t^V��G�����t^_����^_���!a���WV��G�����t^V��G�����t^_�%���^_��,�!�:��� �Ǫ��� �Ǫ��� ���WV��G�����t^V��G�����t^_�~���^_��<1t<2t<3tN�����-�*�!�>�3u��l���> �Ǫ�>�1t���/ �Ǫ���' �>�3t�Ǫ�>�2t��� �Ǫ�Ɓ�l��� �7��
���0���0�Ī�WV��G�����t^V��G�����t^_�3���^_��  �����W� ��_�� t��� v	.�	�������WV��G�����t^V��G�����t^_� ��^_��  ���������W����<t	:�t����_N.��5�����_���>� t
N.�����  ����V����;�t�< t.:�u�A;�u�< t.:�t���^.�	��^��WV��G�����t^V��G�����t^_� ��^_��  ���������W���`����������_��>� t	.��g���� u�� ���� u������ڋ�V���ڬ< t;�t��^.�	�(N�< t	�� t�I��^�U�WV��G�0�.�t^V��G�3�.�t^_� ��^_��  �6������W�'�6��������_�a�>� t	.���6�� u������ڋ�V���ڬ< t;�t
��^�  �0�NW�< tV�.��t^F��_�܋�^_^��+Ë+�@�}��WV��G�:�8�t^V��G�=�8�t^_�Q���^_��  �����W������_��>� t	.��������  V���ڬ< tA��^�����WV��G�B�@�t^V��G�E�@�t^_����^_��  �����W���h���L��� �H�L _�#�>� t	.��l�����  �J+V���ڬ< t�<	t�<+t<-u	.�J-��N�<9<0r� ,0�؋�RS�
 ��[ZË���< t< t�<	t�_N.��^���H�>K+t�>J+tP�-�X�0��>J+t(;�}�ыȋ>K+tP�-�X+����>J+t�;�t���������< t�<	t��  �>L t�K+<+t<-u�K-��N�<9<0r� ,0�؋�RS�
 ��[ZË���<t:�t< t�<	t�_N.��J_N.��?ù  �<t�:�t�A��ì< t�<	t�N�  �<t�:�t< t�<	t��~�A��ð ���ǻ\+ã�>�� t
�> t��� ��ؾ ��ؾ �.��, ��� t���Îؾ  �<Mt�� ��= �r����	�!� ��D.9uٴ �D� ��.��� ��.��؎��������tv�>�����tg�>�����tX�>�����tI�>�����t:�>�����t+�>�����t�>����t����.�6�2�����.�6�\.����< t���< t":�u�N�؎������\� �t���؋����w��� �_.�>�� t�.�>q� t�.�>� t����؋����< u��N< t��< u��N< u�.�<.�.�.� +ʃ��� u�΋�+ʰ �.��4��؎�N��.�II.� ;�u	.����\�&�%�� u����.��L�!�ǋ+Ë;�}	� �<��OO� �����RVW���ێÎ؋ǋ���.��< tK:�t���WV���ێÎ؋�O��.���t��^_��^_�؎���NV�����^�Ρ�ȋ�밪�_^��  VW�\�  �A< u�A�_^Z�����؋��< t.�>q� u� ��.�>� u� ���.�����lG� �\�II��������l�� �� J�!�l�.�ȎЎ؎�.�����M��'� ��� �d � �
 � 0���  ��0�>M�u	<0t�M ����<z<ar, �<Z<Ar �WV������ع  �< tA���^_�PSQRVW������=  u�, ����  �����؋��.���t��< u��< t����<=u�F�6�� ��_^ZY[X�<%u#�</u��W�:���_�� t��� ����N�%�                                                                                                                                