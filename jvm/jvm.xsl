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
    
    <xsl:variable name="vars">
      <xsl:for-each select="//args/arg">        
        <var name="i{position()-1}" val="{.}"/>
      </xsl:for-each>
    </xsl:variable>
    <xsl:variable name="stack" select="'123:'"/>
    
    <html>
      <body>
        
        <h3>Code:</h3>        
        <pre>      
        <xsl:value-of select="code"/>
        </pre>
        
        <h3>Arguments:</h3>      
        <xsl:for-each select="exslt:node-set($vars)/var">        
          <xsl:value-of select="@val"/><br/>
        </xsl:for-each>
        
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
    <xsl:param name="stack"/>
    <xsl:variable name="statementTail" select="normalize-space(substring-after($code, ': '))"/>
    <xsl:variable name="stackTail" select="normalize-space(substring-after($stack, ':'))"/>
    <xsl:variable name="stackHead" select="normalize-space(substring-before($stack, ':'))"/>
    <xsl:choose>
    <xsl:when test="starts-with($statementTail,'ireturn')">
      <xsl:value-of select="$statementTail"/><br/>
      <h3>Result:</h3> 
      <!--<xsl:value-of select="exslt:node-set($vars)/var[@name='i0']/@val"/>-->
      <xsl:value-of select="$stackHead"/>      
    </xsl:when>
    <xsl:otherwise>
      <xsl:variable name="statementRaw" select="substring-before($statementTail, ': ')"/>
      <xsl:variable name="statement" select="substring-before($statementRaw, ' ')"/>    
      <xsl:value-of select="$statement"/><br/>
      
        <xsl:choose>   
                 
          <xsl:when test="starts-with($statement,'iload_')">
            <xsl:variable name="varName" select="concat('i',substring-after($statement,'iload_'))"/>
            <xsl:variable name="newStackHead" select="exslt:node-set($vars)/var[@name=$varName]/@val"/>                        
            <xsl:call-template name="process">
              <xsl:with-param name="code"  select='$statementTail'  />
              <xsl:with-param name="vars"  select='$vars'  />
              <xsl:with-param name="stack"  select="concat($newStackHead,':',$stack)"  />
            </xsl:call-template>
          </xsl:when>  
          
          <xsl:when test="starts-with($statement,'iconst_1')">
            <xsl:variable name="newStackHead" select="substring-after($statement,'_')"/>                        
            <xsl:call-template name="process">
              <xsl:with-param name="code"  select='$statementTail'  />
              <xsl:with-param name="vars"  select='$vars'  />
              <xsl:with-param name="stack"  select="concat($newStackHead,':',$stack)"  />
            </xsl:call-template>
          </xsl:when> 
          
          <xsl:when test="starts-with($statement,'iadd')">            
            <xsl:variable name="stackTail2" select="normalize-space(substring-after($stackTail, ':'))"/>
            <xsl:variable name="stackHead2" select="normalize-space(substring-before($stackTail, ':'))"/>
            <xsl:variable name="newStackHead" select="number($stackHead)+number($stackHead2)"/>
            <xsl:call-template name="process">
              <xsl:with-param name="code"  select='$statementTail'  />
              <xsl:with-param name="vars"  select='$vars'  />
              <xsl:with-param name="stack"  select="concat($newStackHead,':',$stack)"  />
            </xsl:call-template>
          </xsl:when>
          
          <xsl:when test="starts-with($statement,'imul')">            
            <xsl:variable name="stackTail2" select="normalize-space(substring-after($stackTail, ':'))"/>
            <xsl:variable name="stackHead2" select="normalize-space(substring-before($stackTail, ':'))"/>
            <xsl:variable name="newStackHead" select="number($stackHead)*number($stackHead2)"/>
            <xsl:call-template name="process">
              <xsl:with-param name="code"  select='$statementTail'  />
              <xsl:with-param name="vars"  select='$vars'  />
              <xsl:with-param name="stack"  select="concat($newStackHead,':',$stack)"  />
            </xsl:call-template>
          </xsl:when>            
            
          <xsl:otherwise>
            <xsl:call-template name="process">
              <xsl:with-param name="code"  select='$statementTail'  />
              <xsl:with-param name="vars"  select='$vars'  />
              <xsl:with-param name="stack"  select='$stack'  />
            </xsl:call-template>
          </xsl:otherwise>  
        </xsl:choose>  
          
      
        
    </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  
</xsl:stylesheet>
