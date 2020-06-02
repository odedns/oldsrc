VERSION 5.00
Begin VB.Form Form1 
   Appearance      =   0  'Flat
   BackColor       =   &H00FFFF80&
   Caption         =   "MyCalc"
   ClientHeight    =   3360
   ClientLeft      =   3045
   ClientTop       =   1830
   ClientWidth     =   4095
   BeginProperty Font 
      Name            =   "MS Sans Serif"
      Size            =   8.25
      Charset         =   177
      Weight          =   700
      Underline       =   0   'False
      Italic          =   0   'False
      Strikethrough   =   0   'False
   EndProperty
   ForeColor       =   &H00808000&
   LinkTopic       =   "Form1"
   PaletteMode     =   1  'UseZOrder
   ScaleHeight     =   3360
   ScaleWidth      =   4095
   Begin VB.CommandButton operator 
      Appearance      =   0  'Flat
      BackColor       =   &H80000005&
      Caption         =   "^"
      Height          =   495
      Index           =   5
      Left            =   3240
      TabIndex        =   19
      Top             =   2760
      Width           =   495
   End
   Begin VB.CommandButton operator 
      Appearance      =   0  'Flat
      BackColor       =   &H80000005&
      Caption         =   "="
      Height          =   495
      Index           =   4
      Left            =   2640
      TabIndex        =   18
      Top             =   2760
      Width           =   495
   End
   Begin VB.CommandButton operator 
      Appearance      =   0  'Flat
      BackColor       =   &H80000005&
      Caption         =   "-"
      Height          =   495
      Index           =   3
      Left            =   3240
      TabIndex        =   17
      Top             =   2160
      Width           =   495
   End
   Begin VB.CommandButton operator 
      Appearance      =   0  'Flat
      BackColor       =   &H80000005&
      Caption         =   "+"
      Height          =   495
      Index           =   2
      Left            =   3240
      TabIndex        =   16
      Top             =   1560
      Width           =   495
   End
   Begin VB.CommandButton NegButton 
      Appearance      =   0  'Flat
      BackColor       =   &H80000005&
      Caption         =   "+/-"
      Height          =   495
      Left            =   3240
      TabIndex        =   15
      Top             =   960
      Width           =   495
   End
   Begin VB.CommandButton operator 
      Appearance      =   0  'Flat
      BackColor       =   &H80000005&
      Caption         =   "/"
      Height          =   495
      Index           =   1
      Left            =   2640
      TabIndex        =   14
      Top             =   2160
      Width           =   495
   End
   Begin VB.CommandButton operator 
      Appearance      =   0  'Flat
      BackColor       =   &H80000005&
      Caption         =   "X"
      Height          =   495
      Index           =   0
      Left            =   2640
      TabIndex        =   13
      Top             =   1560
      Width           =   495
   End
   Begin VB.CommandButton decPoint 
      Appearance      =   0  'Flat
      BackColor       =   &H80000005&
      Caption         =   "."
      Height          =   495
      Left            =   2040
      TabIndex        =   12
      Top             =   2760
      Width           =   495
   End
   Begin VB.CommandButton clear 
      Appearance      =   0  'Flat
      BackColor       =   &H80000005&
      Caption         =   "C"
      Height          =   495
      Left            =   2640
      TabIndex        =   11
      Top             =   960
      Width           =   495
   End
   Begin VB.CommandButton Number 
      Appearance      =   0  'Flat
      BackColor       =   &H80000005&
      Caption         =   "0"
      Height          =   495
      Index           =   0
      Left            =   840
      TabIndex        =   10
      Top             =   2760
      Width           =   1095
   End
   Begin VB.CommandButton Number 
      Appearance      =   0  'Flat
      BackColor       =   &H80000005&
      Caption         =   "8"
      Height          =   495
      Index           =   8
      Left            =   1440
      TabIndex        =   9
      Top             =   2160
      Width           =   495
   End
   Begin VB.CommandButton Number 
      Appearance      =   0  'Flat
      BackColor       =   &H80000005&
      Caption         =   "9"
      Height          =   495
      Index           =   9
      Left            =   2040
      TabIndex        =   8
      Top             =   2160
      Width           =   495
   End
   Begin VB.CommandButton Number 
      Appearance      =   0  'Flat
      BackColor       =   &H80000005&
      Caption         =   "2"
      Height          =   495
      Index           =   2
      Left            =   1440
      TabIndex        =   7
      Top             =   960
      Width           =   495
   End
   Begin VB.CommandButton Number 
      Appearance      =   0  'Flat
      BackColor       =   &H80000005&
      Caption         =   "3"
      Height          =   495
      Index           =   3
      Left            =   2040
      TabIndex        =   6
      Top             =   960
      Width           =   495
   End
   Begin VB.CommandButton Number 
      Appearance      =   0  'Flat
      BackColor       =   &H80000005&
      Caption         =   "4"
      Height          =   495
      Index           =   4
      Left            =   840
      TabIndex        =   5
      Top             =   1560
      Width           =   495
   End
   Begin VB.CommandButton Number 
      Appearance      =   0  'Flat
      BackColor       =   &H80000005&
      Caption         =   "5"
      Height          =   495
      Index           =   5
      Left            =   1440
      TabIndex        =   4
      Top             =   1560
      Width           =   495
   End
   Begin VB.CommandButton Number 
      Appearance      =   0  'Flat
      BackColor       =   &H80000005&
      Caption         =   "6"
      Height          =   495
      Index           =   6
      Left            =   2040
      TabIndex        =   3
      Top             =   1560
      Width           =   495
   End
   Begin VB.CommandButton Number 
      Appearance      =   0  'Flat
      BackColor       =   &H80000005&
      Caption         =   "7"
      Height          =   495
      Index           =   7
      Left            =   840
      TabIndex        =   2
      Top             =   2160
      Width           =   495
   End
   Begin VB.CommandButton Number 
      Appearance      =   0  'Flat
      BackColor       =   &H80000005&
      Caption         =   "1"
      Height          =   495
      Index           =   1
      Left            =   840
      TabIndex        =   1
      Top             =   960
      Width           =   495
   End
   Begin VB.TextBox TxtDisplay 
      Appearance      =   0  'Flat
      BackColor       =   &H00FFFFFF&
      ForeColor       =   &H00000000&
      Height          =   495
      Left            =   840
      TabIndex        =   0
      Text            =   "0"
      Top             =   240
      Width           =   2655
   End
End
Attribute VB_Name = "Form1"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit
Dim Num1, Num2, Result  As Double
Dim oper, prevOper, CurrNum
Dim LastInput, NegFlag, StackFlag
Dim operInx, numInx, NumOperands  As Integer
Dim operStack(5)
Dim numStack(5) As Double


Private Function Calc_Expr(ByVal Num1 As Double, ByVal oper, ByVal Num2 As Double) As Double

            Select Case oper
                Case "+"
                    Calc_Expr = Num1 + Num2
                            
                Case "-"
                    Calc_Expr = Num1 - Num2

                Case "X"
                    Calc_Expr = Num1 * Num2

                Case "/"
                    Calc_Expr = Num1 / Num2

                Case "^"
                    Calc_Expr = Num1 ^ Num2
                Case "="
                    Calc_Expr = Num2
                    
            End Select
    
End Function

Private Sub clear_Click()
    CurrNum = "0"
    LastInput = "EMPTY"
    TxtDisplay.Text = "0"
    numInx = 1
    operInx = 1
    Result = 0
    Num1 = 0
    Num2 = 0
    NumOperands = 0
End Sub

Private Function CompOper(ByVal oper1, ByVal oper2) As Integer
Dim op1, op2 As Integer
    
        op1 = OperVal(oper1)
        op2 = OperVal(oper2)

    CompOper = op1 - op2
End Function

Private Sub decPoint_Click()

    CurrNum = CurrNum + decPoint.Caption
End Sub

Private Sub Form_Load()
CurrNum = ""
oper = " "
Num1 = 0
Num2 = 0
numInx = 1
operInx = 1
Result = 0
NumOperands = 0
LastInput = "EMPTY"
StackFlag = True
NegFlag = False
End Sub

Private Sub NegButton_Click()
    NegFlag = Not NegFlag
    If (LastInput = "NUMBER") Then

        Select Case NegFlag
            Case True
                If (CurrNum > 0) Then
                    CurrNum = "-" + CurrNum
                End If

            Case False
                If (CurrNum < 0) Then
                    CurrNum = CurrNum * -1
                End If

         End Select
        TxtDisplay.Text = CurrNum
    End If
End Sub

Private Sub Number_Click(index As Integer)

    If CurrNum <> "0" Then
        CurrNum = CurrNum + Number(index).Caption
    Else
        CurrNum = Number(index).Caption
    End If
    LastInput = "NUMBER"
    TxtDisplay.Text = CurrNum
End Sub

Private Sub operator_Click(index As Integer)
Dim prevOper, oper
Dim stat As Integer

        
    oper = operator(index).Caption
    If (LastInput = "OPERATOR") Then
        prevOper = pop_oper()
        NumOperands = NumOperands - 1
    Else
        Result = CurrNum
        push_num (Result)
    End If

    NumOperands = NumOperands + 1
    Select Case NumOperands
        Case 1
                push_oper (oper)
        Case 2
                prevOper = pop_oper()
                stat = CompOper(oper, prevOper)
                If (stat > 0 And oper <> "=") Then
                    push_oper (prevOper)
                    push_oper (oper)
                Else
                    Num2 = pop_num()
                    Num1 = pop_num()
                    Result = Calc_Expr(Num1, prevOper, Num2)
                    push_num (Result)
                    If (oper <> "=") Then
                        push_oper (oper)
                    End If
                    NumOperands = 1
                End If
        Case Else
            Do While NumOperands > 2
                prevOper = pop_oper()
                Num2 = pop_num()
                Num1 = pop_num()
                Result = Calc_Expr(Num1, prevOper, Num2)
                push_num (Result)
                If (oper <> "=") Then
                    push_oper (oper)
                End If
                NumOperands = NumOperands - 1
             Loop
            End Select
                  
    LastInput = "OPERATOR"
    CurrNum = ""

    If (oper = "=") Then
        LastInput = "NUMBER"
        Result = pop_num()
        If (StackFlag = True) Then
            CurrNum = Result
        End If
        TxtDisplay.Text = CurrNum
        NumOperands = 0
    End If
EndLabel:
End Sub

Private Function OperVal(ByVal oper) As Integer

    Select Case oper
        Case "^"
            OperVal = 3
        Case "X"
             OperVal = 2
        Case "/"
            OperVal = 2
        Case "+"
            OperVal = 1
        Case "-"
            OperVal = 1
    End Select

End Function

Private Function pop_num()

    If (numInx > 1) Then
        numInx = numInx - 1
        pop_num = numStack(numInx)
    Else
        pop_num = 0
        StackFlag = False
    End If
   
End Function

Private Function pop_oper()

    If (operInx > 1) Then
        operInx = operInx - 1
        pop_oper = operStack(operInx)
    Else
        pop_oper = -1
    End If

End Function

Private Sub push_num(ByVal Num As Double)

    If (numInx < 5) Then
        numStack(numInx) = Num
        numInx = numInx + 1
    End If
        
End Sub

Private Sub push_oper(ByVal oper)

        If (operInx < 5) Then
            operStack(operInx) = oper
            operInx = operInx + 1
        End If
   
End Sub

Private Sub TxtDisplay_Change()
     TxtDisplay.Text = CurrNum
End Sub

