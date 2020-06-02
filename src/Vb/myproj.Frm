VERSION 4.00
Begin VB.Form Form1 
   BackColor       =   &H00FFFFFF&
   Caption         =   "MyProg"
   ClientHeight    =   4140
   ClientLeft      =   1635
   ClientTop       =   2100
   ClientWidth     =   6690
   ForeColor       =   &H00000000&
   Height          =   4830
   Left            =   1575
   LinkTopic       =   "Form1"
   ScaleHeight     =   4140
   ScaleWidth      =   6690
   Top             =   1470
   Width           =   6810
   Begin VB.Menu mnuFile 
      Caption         =   "&File"
      Index           =   1
      Begin VB.Menu mnuFileOpen 
         Caption         =   "&Open"
         Index           =   2
      End
      Begin VB.Menu mnuSaveFile 
         Caption         =   "&Save "
         Index           =   3
      End
      Begin VB.Menu mnuFileSaveAs 
         Caption         =   "Save &As"
         Index           =   4
      End
      Begin VB.Menu mnuBlank1 
         Caption         =   "-"
      End
      Begin VB.Menu mnuExit 
         Caption         =   "E&xit"
         Index           =   5
      End
   End
   Begin VB.Menu mnuControls 
      Caption         =   "&Controls"
      Index           =   11
      Begin VB.Menu mnuListBox 
         Caption         =   "&ListBox"
         Index           =   12
      End
   End
End
Attribute VB_Name = "Form1"
Attribute VB_Creatable = False
Attribute VB_Exposed = False

Private Sub mnuExit_Click(Index As Integer)
    MsgBox ("Exiting ....")
    End
End Sub

Private Sub mnuFileOpen_Click(Index As Integer)
    MsgBox ("File Open")

End Sub


Private Sub mnuFileSaveAs_Click(Index As Integer)
    MsgBox ("File Save As")
End Sub


Private Sub mnuListBox_Click(Index As Integer)
    ListForm.Show
End Sub

Private Sub mnuSaveFile_Click(Index As Integer)
    MsgBox ("File Save")
End Sub


