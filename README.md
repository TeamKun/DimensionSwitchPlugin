# DimensionSwitchPlugin

## 概要
一定時間ごとにプレイヤーのディメンジョンを変更します。（例えば、３分ごとにオーバーワールドとネザーを行き来するなど）

## コマンド一覧
### Start コマンド
```
> /dimension start <interval>
```
ディメンジョンをスイッチするカウントダウンを指定された秒数で開始します。  
interval は整数で指定し、単位は秒です。
### Interval コマンド
```
> /dimension interval <interval>
```
カウントダウンの秒数を変更します。  
interval は整数で指定し、単位は秒です。
### End コマンド
```
> /dimension end
```
カウントダウンを終了します。

## 権限
権限はデフォルトで OP です。

## 動作条件
PaperMC 1.15.2
