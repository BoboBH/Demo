
USE ReoHK_MSCRM
GO
IF EXISTS (SELECT * FROM DBO.SYSOBJECTS WHERE id = object_id(N'[dbo].[fn_getfxrate]') AND xtype IN (N'FN', N'IF', N'TF'))
   DROP FUNCTION dbo.fn_getfxrate
GO
CREATE FUNCTION dbo.fn_getfxrate(
	@f_currency VARCHAR(3),
	@t_currency VARCHAR(3))
RETURNS DECIMAL(19,4) AS
BEGIN
   DECLARE @r_rate DECIMAL(19,4)
   DECLARE @l_criteria VARCHAR(20)
   SET @r_rate = 1.0
   SET @l_criteria = @f_currency + '_' + @t_currency + '%'
   IF @f_currency = @t_currency
      RETURN 1.0   
   SELECT top 1 @r_rate= new_sellrate FROM dev02.reohk_mscrm.dbo.new_yfffxrate WHERE new_name like @l_criteria
   ORDER BY new_effectivedate DESC
   RETURN @r_rate
END