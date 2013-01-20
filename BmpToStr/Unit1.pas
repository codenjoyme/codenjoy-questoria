unit Unit1;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, ExtDlgs, StdCtrls;

type
  TfmForm1 = class(TForm)
    btLoadImage: TButton;
    opdLoadImage: TOpenPictureDialog;
    sdTextFile: TSaveDialog;
    edMask: TEdit;
    debug: TMemo;
    procedure btLoadImage_onClick(Sender: TObject);
  private
  procedure BmpToText(BmpFileName: String; TxtFileName: String);
  public
    { Public declarations }
  end;

var
  fmForm1: TfmForm1;

implementation

{$R *.dfm}
//--------------------------------------------------------------------------------------------------
procedure TfmForm1.btLoadImage_onClick(Sender: TObject);
begin
    if ((opdLoadImage.Execute) and (FileExists(opdLoadImage.FileName))) then begin
        if (sdTextFile.Execute) then begin
            BmpToText(opdLoadImage.FileName, sdTextFile.FileName);
        end;
    end;
end;
//--------------------------------------------------------------------------------------------------
procedure TfmForm1.BmpToText(BmpFileName: String; TxtFileName: String);
var Lines: TStrings;
    Bmp:TbitMap;
    ch:char;
    col:byte;
    x, y, i:integer;
    str:string;

    symbols:Array[0..255] of char;
    colors:Array[0..255] of byte;
    firstColor, currentColor:byte;
    len:integer;
begin
    Bmp:=TbitMap.Create;
    Bmp.PixelFormat:=pf8bit;
    Bmp.LoadFromFile(BmpFileName);

    len:=0;
    firstColor:=Bmp.Canvas.Pixels[0, 0];
    currentColor:=firstColor;
    repeat
       colors[len]:=currentColor;
       symbols[len]:=edMask.Text[len + 1];
//       debug.Lines.Add('symbols[' + IntToStr(len) + ']=' + symbols[len]);
//       debug.Lines.Add(IntToStr(firstColor) + '=' + IntToStr(currentColor));
       inc(len);
       currentColor:=Bmp.Canvas.Pixels[len, 0];
    until ((len >= Bmp.Width) or ((len > 1) and (currentColor = firstColor)));
    dec(len);

//    debug.Lines.Add('---------------');
//    for i:=0 to len do begin
//       debug.Lines.Add('symbols[' + IntToStr(i) + ']=' + symbols[i]);
//    end;
//    debug.Lines.Add('---------------');

    Lines := TStringList.Create;
    try
        for y:=0 to (Bmp.Height - 1) do begin
            str:='';
            for x:=0 to (Bmp.Width - 1) do begin
                col := Bmp.Canvas.Pixels[x, y];
                for i:=0 to len do begin
                    if (colors[i] = col) then begin
                        ch:=symbols[i];
                    end;
                end;
                str:=str + ch;
            end;
            Lines.Add(str);
        end;
        Lines.SaveToFile(TxtFileName);
     finally
        Lines.Free;
     end;
end;
//--------------------------------------------------------------------------------------------------
end.

