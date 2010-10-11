<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:exslt="http://exslt.org/common">
  <xsl:output
    method="html"
    version="1.0"
    omit-xml-declaration="no"
    indent="yes"
    />

  <xsl:param name="debug" select="false()"/>

  <xsl:template match="/class">
    <xsl:variable name="code" select="normalize-space(code)"/>
    <xsl:variable name="arg" select="normalize-space(arg)"/>
    <xsl:variable name="vars">
      <var name="i0" val="{$arg}"/>
    </xsl:variable>
    <xsl:variable name="stack" select="'':''"/>
    
    
    <html>
      <body>
        <h3>Code:</h3>        
        <pre>      
        <xsl:value-of select="code"/>
        </pre>
        <h3>Trace:</h3>
        <p>
          <xsl:call-template name="process">
            <xsl:with-param name="code"  select='$code'  />
            <xsl:with-param name="vars"  select='$vars'  />
            <xsl:with-param name="stack"  select='$stack'  />
          </xsl:call-template>
        </p>
      </body>
    </html>
  </xsl:template>


  <xsl:template name="process">
    <xsl:param name="code"/>
    <xsl:param name="vars"/>
    <xsl:variable name="statementTail" select="normalize-space(substring-after($code, ': '))"/>
    <xsl:choose>
    <xsl:when test="starts-with($statementTail,'ireturn')">
      <h3>Result:</h3> 
      <xsl:value-of select="exslt:node-set($vars)/var[@name='i0']/@val"/>      
    </xsl:when>
    <xsl:otherwise>
      <xsl:variable name="statementRaw" select="substring-before($statementTail, ': ')"/>
      <xsl:variable name="statement" select="substring-before($statementRaw, ' ')"/>    
      <xsl:value-of select="$statement"/><br/>
        <xsl:call-template name="process">
          <xsl:with-param name="code"  select='$statementTail'  />
          <xsl:with-param name="vars"  select='$vars'  />
        </xsl:call-template>
    </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  
</xsl:stylesheet>
