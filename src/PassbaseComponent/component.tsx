import React from 'react'
import { NativeProps, NativeComponent } from './native-component'

type Props = NativeProps & { style: object }

const RefComponent = (props: Props, forwardedRef?: React.Ref<React.Component<NativeProps>>) => {
  const { style, ...restProps } = props || { style: {} }

  const nativeProps = {
    ...restProps,
    style: {
      ...style,
      width: 300,
      height: 60
    }
  }
  return <NativeComponent {...nativeProps} ref={forwardedRef} />
}

export const Component = React.forwardRef(RefComponent)
Component.displayName = 'PassbaseComponent'
