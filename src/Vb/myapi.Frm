VERSION 4.00
Begin VB.Form Form1 
   Caption         =   "Form1"
   ClientHeight    =   4245
   ClientLeft      =   1650
   ClientTop       =   1785
   ClientWidth     =   6690
   Height          =   4650
   Left            =   1590
   LinkTopic       =   "Form1"
   ScaleHeight     =   0
   ScaleWidth      =   0
   Top             =   1440
   Width           =   6810
   Begin VB.Label mbtotal 
      Height          =   375
      Left            =   3240
      TabIndex        =   2
      Top             =   1440
      Width           =   975
   End
   Begin VB.Label mbfree 
      Height          =   495
      Left            =   3240
      TabIndex        =   1
      Top             =   840
      Width           =   855
   End
   Begin VB.Label Percent 
      Height          =   375
      Left            =   3360
      TabIndex        =   0
      Top             =   360
      Width           =   615
   End
End
Attribute VB_Name = "Form1"
Attribute VB_Creatable = False
Attribute VB_Exposed = False
Option Explicit



Private Sub Drive1_Change()

End Sub


Private Sub Form_Load()
Dim percent_used As Long
Dim mb_total As Long
Dim mb_free As Long


Call myfree("c", percent_used, mb_total, mb_free)

Percent.Caption = "percent " + percent_used
mbfree.Caption = mb_free
mbtotal.Caption = mb_total

End Sub


Private Sub ProgressBar1_MouseDown(Button As Integer, Shift As Integer, x As Single, y As Single)

End Sub


