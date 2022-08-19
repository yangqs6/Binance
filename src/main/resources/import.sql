
drop table if exists bank;


drop table if exists kline;


create table bank(

     load_id varchar(55) ,
     symbol varchar(30),
     open_time varchar(30),
     open_ varchar(30),
     high varchar(30),
     low varchar(30),
     close_ varchar(30),
     volume varchar(30),
     close_time VARCHAR(30),
     quote_asset_volume varchar(30),
     num_of_trades varchar(30),
     buy_base_volume varchar(30),
     buy_quote_volume varchar(30),
     ignore_ varchar(30),
     primary key(load_id, symbol,open_time,close_time) -- 这个就是复合主键

);

create table kline(
      load_id varchar(55),
      symbol varchar(30),
      open_time datetime,
      open_ DOUBLE,
      high DOUBLE,
      low DOUBLE,
      close_ DOUBLE,
      volume DOUBLE,
      close_time datetime
);

insert into kline(load_id, symbol, open_time, open_, high, low, close_, volume, close_time) values
(
        'e2501050-5298-4bbf-a8e2-9308aef0449c',
        'BTC',
        '2018-04-12 10:45:00',
        7885.98,
        7910.01,
        7875,
        7900.97,
        124.348422,
        '2018-04-12 10:45:59'
);


