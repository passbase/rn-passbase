import React from 'react'
import { NativeProps, NativeComponent } from './native-component'

type Props = any

const RefComponent = (props: Props, forwardedRef?: React.Ref<React.Component<NativeProps>>) => {
  const { style, ...restProps } = props || {} as any
  const styles = { width: 300, height: 60, ...style }
  restProps.style = styles
  return <NativeComponent
    {...restProps}
    ref={forwardedRef}
  />
}

export const Component = React.forwardRef(RefComponent)
Component.displayName = 'PassbaseComponent'
