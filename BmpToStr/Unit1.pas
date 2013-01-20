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
    c:char;
    col:byte;
    x, y:integer;
    str:string;
begin
    Bmp:=TbitMap.Create;
    Bmp.PixelFormat:=pf8bit;
    Bmp.LoadFromFile(BmpFileName);

    Lines := TStringList.Create;
    try
        for y:=0 to (Bmp.Height - 1) do begin
            str:='';
            for x:=0 to (Bmp.Width - 1) do begin
                col := Bmp.Canvas.Pixels[x, y];
                str:=str + char(col);
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
