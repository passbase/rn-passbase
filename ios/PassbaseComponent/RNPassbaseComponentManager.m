#import <React/RCTViewManager.h>

@interface RCT_EXTERN_MODULE(RNPassbaseComponentManager, RCTViewManager)

+ (BOOL)requiresMainQueueSetup
{
  return YES;
}

- (dispatch_queue_t)methodQueue
{
  return dispatch_get_main_queue();
}

@end
